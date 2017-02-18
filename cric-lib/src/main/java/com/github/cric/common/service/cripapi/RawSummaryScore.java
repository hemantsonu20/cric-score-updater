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
package com.github.cric.common.service.cripapi;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RawSummaryScore {

    @JsonProperty("matchStarted")
    private boolean matchStarted;

    @JsonProperty("team-1")
    private String firstTeam;

    @JsonProperty("team-2")
    private String secondTeam;

    @JsonProperty("type")
    private String matchType;
    
    @JsonProperty("score")
    private String score;
    
    @JsonProperty("innings-requirement")
    private String inningRequirement;

    public boolean isMatchStarted() {

        return matchStarted;
    }

    public String getFirstTeam() {

        return firstTeam;
    }

    public String getSecondTeam() {

        return secondTeam;
    }

    public String getMatchType() {

        return matchType;
    }

    public String getScore() {

        return score;
    }

    public String getInningRequirement() {

        return inningRequirement;
    }

    public RawSummaryScore setMatchStarted(boolean matchStarted) {

        this.matchStarted = matchStarted;
        return this;
    }

    public RawSummaryScore setFirstTeam(String firstTeam) {

        this.firstTeam = firstTeam;
        return this;
    }

    public RawSummaryScore setSecondTeam(String secondTeam) {

        this.secondTeam = secondTeam;
        return this;
    }

    public RawSummaryScore setMatchType(String matchType) {

        this.matchType = matchType;
        return this;
    }

    public RawSummaryScore setScore(String score) {

        this.score = score;
        return this;
    }

    public RawSummaryScore setInningRequirement(String inningRequirement) {

        this.inningRequirement = inningRequirement;
        return this;
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("matchStarted", matchStarted)
                .append("firstTeam", firstTeam)
                .append("secondTeam", secondTeam)
                .append("matchType", matchType)
                .append("score", score)
                .append("inningRequirement", inningRequirement)
                .build();
    }
}
