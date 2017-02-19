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

import javax.swing.JDialog;
import javax.swing.Timer;

public class AutoHideTimer {
    
    private Timer timer;
    
    /**
     * 
     * 
     * @param popup to be hidden
     * @param time in seconds
     */
    public AutoHideTimer(JDialog popup, int time) {
        timer = new Timer(time * 1000, e -> popup.setVisible(false));
        timer.setRepeats(false);
    }
    
    public void start() {
        timer.restart();
    }

    public void stop() {

        timer.stop();
    }
}
