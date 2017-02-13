/**
 *   Copyright 2017 Pratapi Hemant Patel
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   
 */
package com.github.cric.service;

import java.util.List;

import com.github.cric.model.BallByBallScore;
import com.github.cric.model.FullScore;
import com.github.cric.model.Match;
import com.github.cric.model.SummaryScore;

public interface ScoreService {

    List<Match> getCurrentMatches();
    
    List<Match> getCurrentMatches(String teamName); 
    
    SummaryScore getSummaryScore(int matchId);

    FullScore getFullScore(int matchId);
    
    BallByBallScore getBallByBallScore(int matchId);
}
