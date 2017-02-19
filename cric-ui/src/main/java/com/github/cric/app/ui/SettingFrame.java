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
package com.github.cric.app.ui;

import java.awt.BorderLayout;
import java.awt.Button;

import javax.swing.JFrame;

import com.github.cric.app.model.Settings;
import com.github.cric.app.service.MainLock;

public class SettingFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private MainLock mainLock;

    private Settings settings = new Settings().setMatchId(1001351);

    public SettingFrame(MainLock mainLock) {

        super();
        setLayout(new BorderLayout());
        setSize(400, 110);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Button b = new Button("Start");
        b.addActionListener(e -> {
            this.submitted();
            b.setEnabled(false);
        });
        add(b);

        this.mainLock = mainLock;
    }

    private void submitted() {

        this.setExtendedState(ICONIFIED);
        mainLock.unlock();
    }

    public Settings getSettings() {

        return settings;
    }

    public boolean hasUpdatedSettings() {

        return (settings != null);
    }

    static {
        JFrame.setDefaultLookAndFeelDecorated(true);
    }
}
