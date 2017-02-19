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
package com.github.cric.ui;

import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.github.cric.common.EnableCommonCricLib;
import com.github.cric.common.EnableCricApiDotCom;

@SpringBootApplication
@EnableCommonCricLib
@EnableCricApiDotCom
public class UiApplication implements CommandLineRunner {

    public static final int DEFAULT_POPUP_FREQUENCY = 20;
    public static final int DEFAULT_POPUP_TIME = 10;

    public static void main(String[] args) {

        new SpringApplicationBuilder(UiApplication.class).headless(false).web(false).run(args);
    }

    @Autowired
    private MainLock mainLock;

    @Autowired
    private ContextService service;

    @Override
    public void run(String... args) throws Exception {

        // List<Match> m = service.getCurrentMatches("INDIA");

        SettingFrame settingsFrame = new SettingFrame(mainLock);
        SwingUtilities.invokeLater(() -> settingsFrame.setVisible(true));

        // wait for settings from the frame
        mainLock.lock();
        if (settingsFrame.hasUpdatedSettings()) {
            service.schedulePopup(settingsFrame.getSettings());
        }
    }
}
// logging in file
// task bar icon, jar icon
