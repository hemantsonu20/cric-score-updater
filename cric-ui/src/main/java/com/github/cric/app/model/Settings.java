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
package com.github.cric.app.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public final class Settings implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int matchId;
    private final int popupTime;
    private final int popupFrequency;
    private final String apiKey;

    public Settings(int matchId, int popupTime, int popupFrequency) {

        this(matchId, popupTime, popupFrequency, null);
    }

    @JsonCreator
    public Settings(@JsonProperty("matchId") int matchId, @JsonProperty("popupTime") int popupTime,
            @JsonProperty("popupFrequency") int popupFrequency, @JsonProperty("apiKey") String apiKey) {

        this.matchId = matchId;
        this.popupTime = popupTime;
        this.popupFrequency = popupFrequency;
        this.apiKey = apiKey;
    }

    public int getMatchId() {

        return matchId;
    }

    public int getPopupTime() {

        return popupTime;
    }

    public int getPopupFrequency() {

        return popupFrequency;
    }

    public String getApiKey() {

        return apiKey;
    }
}
