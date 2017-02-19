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

import com.github.cric.app.UiApplication;

/**
 * @author heman
 *
 */
public class Settings {

    private int matchId;
    private int popupTime = UiApplication.DEFAULT_POPUP_TIME;
    private int popupFrequency = UiApplication.DEFAULT_POPUP_FREQUENCY;

    public int getMatchId() {

        return matchId;
    }

    public int getPopupTime() {

        return popupTime;
    }

    public int getPopupFrequency() {

        return popupFrequency;
    }

    public Settings setMatchId(int matchId) {

        this.matchId = matchId;
        return this;
    }

    public Settings setPopupTime(int popupTime) {

        this.popupTime = popupTime;
        return this;
    }

    public Settings setPopupFrequency(int popupFrequency) {

        this.popupFrequency = popupFrequency;
        return this;
    }
}
