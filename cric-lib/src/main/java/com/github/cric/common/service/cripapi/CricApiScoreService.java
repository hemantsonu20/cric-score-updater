package com.github.cric.common.service.cripapi;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cric.common.exception.CricException;
import com.github.cric.common.model.BallByBallScore;
import com.github.cric.common.model.FullScore;
import com.github.cric.common.model.Match;
import com.github.cric.common.model.SummaryScore;
import com.github.cric.common.model.Team;
import com.github.cric.common.service.ScoreService;

@Service
public class CricApiScoreService implements ScoreService {

    private final CripApiRemoteService remoteService;

    @Autowired
    public CricApiScoreService(CripApiRemoteService remoteService) {

        this.remoteService = remoteService;
    }

    /**
     * Return list of matches for given team in sorted order of match date / time
     * 
     */
    @Override
    public List<Match> getCurrentMatches(String teamName) {

        Team team = Team.fromName(teamName);
        if (team == null) {
            throw new CricException(String.join("", "unknown team name: ", teamName));
        }
        return remoteService.getCurrentMatches()
                .stream()
                .filter(m -> m.getFirstTeam() == team || m.getSecondTeam() == team)
                .collect(Collectors.toList());
    }

    @Override
    public SummaryScore getSummaryScore(int matchId) {

        return null;
    }

    @Override
    public FullScore getFullScore(int matchId) {

        return null;
    }

    @Override
    public BallByBallScore getBallByBallScore(int matchId) {

        return null;
    }
}
