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
package com.github.cric.app;

import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.github.cric.app.service.ContextService;
import com.github.cric.app.service.MainLock;
import com.github.cric.app.ui.SettingFrame;
import com.github.cric.common.EnableCommonCricLib;
import com.github.cric.common.EnableCricApiDotCom;
import com.github.cric.common.service.ScoreService;

@SpringBootApplication
@EnableCommonCricLib
@EnableCricApiDotCom
public class UiApplication implements CommandLineRunner {

    public static final int DEFAULT_POPUP_FREQUENCY = 20;
    public static final int DEFAULT_POPUP_TIME = 10;

    @Autowired
    private MainLock mainLock;

    @Autowired
    private ContextService contextService;

    @Autowired
    private ScoreService scoreService;

    @Override
    public void run(String... args) throws Exception {

        SettingFrame settingsFrame = new SettingFrame(scoreService, mainLock);
        SwingUtilities.invokeLater(() -> settingsFrame.setVisible(true));

        // wait for settings from the frame
        mainLock.lock();
        if (settingsFrame.hasUpdatedSettings()) {
            contextService.schedulePopup(settingsFrame.getSettings());
        }
    }

    public static void main(String[] args) {

        new SpringApplicationBuilder(UiApplication.class).headless(false).web(false).run(args);
    }
}
// logging in file
// task bar icon, jar icon
// store current values from file
// Help menu
