package com.github.cric.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cric.common.model.BallByBallScore;
import com.github.cric.common.model.FullScore;
import com.github.cric.common.model.Match;
import com.github.cric.common.model.SummaryScore;

@Service
public class CricApiScoreService implements ScoreService {

    private RemoteService remoteService;

    @Autowired
    public CricApiScoreService(RemoteService remoteService) {
        this.remoteService = remoteService;
    }

    @Override
    public List<Match> getCurrentMatches(String teamName) {

        // TODO filter on base of team name
        return remoteService.getCurrentMatches();
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
