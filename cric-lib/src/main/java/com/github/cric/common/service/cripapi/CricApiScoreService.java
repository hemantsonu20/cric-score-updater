package com.github.cric.common.service.cripapi;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cric.common.model.Match;
import com.github.cric.common.model.SummaryScore;
import com.github.cric.common.service.ScoreService;

@Service
public class CricApiScoreService implements ScoreService {

    private final CripApiRemoteService remoteService;

    @Autowired
    public CricApiScoreService(CripApiRemoteService remoteService) {

        this.remoteService = remoteService;
    }

    /**
     * Return list of matches for given team in sorted order of match date /
     * time
     */
    @Override
    public List<Match> getCurrentMatches() {

        return remoteService.getCurrentMatches();
    }

    @Override
    public SummaryScore getSummaryScore(int matchId) {

        return remoteService.getSummaryScore(matchId);
    }
}
