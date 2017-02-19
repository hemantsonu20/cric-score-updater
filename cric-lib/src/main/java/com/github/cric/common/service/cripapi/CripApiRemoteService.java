package com.github.cric.common.service.cripapi;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cric.common.exception.RemoteException;
import com.github.cric.common.model.Match;
import com.github.cric.common.model.SummaryScore;
import com.github.cric.common.model.Team;

@Service
class CripApiRemoteService {

    private final RestTemplate restTemplate;
    private final CricApiUrlConfig urlConfig;
    private final ObjectMapper mapper;

    CripApiRemoteService(@Qualifier("cricApiRestTemplate") RestTemplate restTemplate, CricApiUrlConfig urlConfig,
            ObjectMapper mapper) {

        this.restTemplate = restTemplate;
        this.urlConfig = urlConfig;
        this.mapper = mapper;
    }

    /**
     * Method to fetch current matches ongoing or some future matches. This
     * method returns matches in sorted order of match date / time.
     * 
     * @return
     */
    List<Match> getCurrentMatches() {

        JsonNode matchesResponse = restTemplate.getForObject(urlConfig.getMatchesApi(), JsonNode.class);

        try {
            List<RawMatch> rawMatches = mapper.readValue(
                    fetchMatchesNode(matchesResponse),
                    new TypeReference<List<RawMatch>>() {
                    });

            return rawMatches
                    .stream()
                    .filter(RawMatch::isMatchStarted)
                    .sorted((r1, r2) -> r1.getDate().compareTo(r2.getDate()))
                    .map(this::map)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RemoteException(matchesResponse.toString(), e);
        }
    }

    /**
     * Returns score of a given match id. Match id will remain unique, So its
     * better to store it some where when you get it and use it in further
     * request.
     * 
     */
    SummaryScore getSummaryScore(int matchId) {

        return map(restTemplate.getForObject(urlConfig.getScoreApi(), RawSummaryScore.class, matchId)).setMatchId(
                matchId);
    }

    private String fetchMatchesNode(JsonNode matchesResponse) {

        JsonNode matchesNode = matchesResponse.get("matches");
        if (null != matchesNode) {
            return matchesNode.toString();
        }
        throw new RemoteException(matchesResponse.toString());
    }

    private Match map(RawMatch raw) {

        return new Match()
                .setMatchId(raw.getMatchId())
                .setMatchStarted(raw.isMatchStarted())
                .setFirstTeam(Team.fromName(raw.getFirstTeam()))
                .setSecondTeam(Team.fromName(raw.getSecondTeam()));
    }

    private SummaryScore map(RawSummaryScore raw) {

        return new SummaryScore()
                .setMatchStarted(raw.isMatchStarted())
                .setFirstTeam(Team.fromName(raw.getFirstTeam()))
                .setSecondTeam(Team.fromName(raw.getSecondTeam()))
                .setMatchType(raw.getMatchType())
                .setScore(raw.getScore())
                .setInningRequirement(raw.getInningRequirement());
    }
}
