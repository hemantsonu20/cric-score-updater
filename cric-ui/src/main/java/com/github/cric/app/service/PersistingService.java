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

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cric.app.UiApplication;
import com.github.cric.app.model.Settings;

public class PersistingService {

    private static final Logger LOG = LoggerFactory.getLogger(PersistingService.class);

    private static final String HOME_DIR = System.getProperty("user.home");
    private static final String SETTING_FILE = ".cric-score-updater";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void saveSettings(Settings settings) {

        try {
            MAPPER.writeValue(new File(HOME_DIR, SETTING_FILE), settings);
            LOG.debug("current configuration persisted");
        } catch (IOException e) {
            LOG.warn("save setting failed", e);
        }
    }

    public Settings getSettings() {

        try {
            File f = new File(HOME_DIR, SETTING_FILE);

            if (f.exists()) {
                return MAPPER.readValue(f, Settings.class);
            }

        } catch (IOException e) {
            LOG.warn("reading from file failed", e);
        }
        return new Settings(0, UiApplication.DEFAULT_POPUP_TIME, UiApplication.DEFAULT_POPUP_FREQUENCY);
    }
}
