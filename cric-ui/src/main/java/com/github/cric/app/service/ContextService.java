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
package com.github.cric.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.cric.app.model.PopupMessage;
import com.github.cric.app.model.Settings;
import com.github.cric.app.ui.NotificationWindow;
import com.github.cric.common.listener.CricContext;
import com.github.cric.common.listener.SummaryScoreResponse;
import com.github.cric.common.model.SummaryScore;

/**
 * @author heman
 *
 */
@Component
public class ContextService {

    // msgs to display
    private PopupMessage popupMessage = new PopupMessage();

    private CricContext context;
    private NotificationWindow popup;
    private MainLock lock;

    @Autowired
    public ContextService(CricContext context, NotificationWindow popup, MainLock lock) {

        this.context = context;
        this.popup = popup;
        this.lock = lock;
    }

    public void schedulePopup(Settings settings) {

        // register new listener and schedule timer
        context.registerSummaryScoreListener(this::consume, settings.getMatchId(), settings.getPopupFrequency());
        AutoHideTimer timer = new AutoHideTimer(popup, settings.getPopupTime());

        while (true) {

            lock.lock();
            popup.showMsg(popupMessage);
            // timer.start();
        }
    }

    private void consume(SummaryScoreResponse s) {

        System.out.println("in consume");
        if (s.hasError()) {
            popupMessage.setHeading("Error").setMessage(s.getError().getMessage()).setRequirement("");
        }
        else {
            parseScore(s.getSummaryScore());
        }
        lock.unlock();
    }

    private void parseScore(SummaryScore summaryScore) {

        String score = summaryScore.getScore();
        String headingPart = "Score";
        String msgPart = "Not Available";
        if (null != score) {

            int index = score.indexOf('(');

            if (-1 != index) {
                headingPart = score.substring(0, index).trim();
                msgPart = score.substring(index).trim();
            }
            else {
                msgPart = score;
            }
        }
        popupMessage
                .setHeading(
                        String.format(
                                "%s (%s V %s)",
                                headingPart,
                                summaryScore.getFirstTeam().getShortName(),
                                summaryScore.getSecondTeam().getShortName()))
                .setMessage(msgPart)
                .setRequirement(summaryScore.getInningRequirement());
    }
}
