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

import java.time.ZonedDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Raw match data as returned from cripapi
 * 
 */
@JsonIgnoreProperties(value = { "squad" }, ignoreUnknown = true)
class RawMatch {

    @JsonProperty("unique_id")
    private int matchId;

    @JsonProperty("matchStarted")
    private boolean matchStarted;

    @JsonProperty("team-1")
    private String firstTeam;

    @JsonProperty("team-2")
    private String secondTeam;

    @JsonProperty("date")
    private ZonedDateTime date;

    public int getMatchId() {

        return matchId;
    }

    public boolean isMatchStarted() {

        return matchStarted;
    }

    public String getFirstTeam() {

        return firstTeam;
    }

    public String getSecondTeam() {

        return secondTeam;
    }

    public ZonedDateTime getDate() {

        return date;
    }

    public RawMatch setMatchId(int matchId) {

        this.matchId = matchId;
        return this;
    }

    public RawMatch setMatchStarted(boolean matchStarted) {

        this.matchStarted = matchStarted;
        return this;
    }

    public RawMatch setFirstTeam(String firstTeam) {

        this.firstTeam = firstTeam;
        return this;
    }

    public RawMatch setSecondTeam(String secondTeam) {

        this.secondTeam = secondTeam;
        return this;
    }

    public RawMatch setDate(ZonedDateTime date) {

        this.date = date;
        return this;
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
        .append(matchId)
        .append(matchStarted)
        .append(firstTeam)
        .append(secondTeam)
        .append(date)
        .build();
    }
}
