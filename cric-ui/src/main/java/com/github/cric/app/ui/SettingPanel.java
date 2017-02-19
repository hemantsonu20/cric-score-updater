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

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.github.cric.app.UiApplication;
import com.github.cric.app.model.Settings;
import com.github.cric.common.model.Match;
import com.github.cric.common.service.ScoreService;

public class SettingPanel extends JPanel {

    
    private static final Logger LOG = LoggerFactory.getLogger(SettingPanel.class);
    
    
    private static final long serialVersionUID = 1L;
    private static final String MATCH_FORMAT = "%s V %s";

    private SettingFrame parent;
    private List<Match> matchList;
    private ScoreService scoreService;

    private JComboBox<String> matchesCombo = new JComboBox<>();
    private JTextField apiKeyField = textField("api key");
    private JTextField popupTimeField = textField(Integer.toString(UiApplication.DEFAULT_POPUP_TIME));
    private JTextField popupFreuencyField = textField(Integer.toString(UiApplication.DEFAULT_POPUP_FREQUENCY));
    
    private JLabel msgLabel = new JLabel("enter api key provided by cricapi.com");

    public SettingPanel(SettingFrame parent, ScoreService scoreService) {

        super();
        this.parent = parent;
        this.scoreService = scoreService;

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(label("Matches", "currently ongoing matches"));
        formPanel.add(matchesCombo);

        formPanel.add(label("Api Key", "get api key from cricapi.com"));
        apiKeyField.setColumns(18);
        formPanel.add(apiKeyField);
        
        formPanel.add(label("Popup Time (sec)", "popup disappears after this time"));
        formPanel.add(popupTimeField);

        formPanel.add(label("Popup Frequency (sec)", "popup will appear repeatedly"));
        formPanel.add(popupFreuencyField);

        JPanel submitPanel = new JPanel();
        submitPanel.add(scheduleButton());
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(formPanel);
        add(submitPanel);
        setAllEnabled(false);
        
        msgLabel.setForeground(Color.MAGENTA);
        msgLabel.setHorizontalAlignment(JLabel.LEFT);
        add(msgLabel);
    }

    private String getMatchItem(Match m) {

        return String.format(MATCH_FORMAT, m.getFirstTeam().getDisplayName(), m.getSecondTeam().getDisplayName());
    }

    private void submitted() {

        int selected = matchesCombo.getSelectedIndex();
        
        if(selected > 0) {
            int matchId = matchList.get(selected).getMatchId();
            int popupTime = Integer.parseInt(popupTimeField.getText());
            int popupFrequency = Integer.parseInt(popupFreuencyField.getText());
            parent.submitted(new Settings(matchId, popupTime, popupFrequency));
            setMessage("notification scheduled, close this window to stop");
        }
        else {
            LOG.warn("no items in match combo");
            parent.submitted(null);
        }
    }

    private JTextField textField(String defaultValue) {

        JTextField field = new JTextField(defaultValue);
        field.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {

                // NO-OP
            }

            @Override
            public void focusGained(FocusEvent e) {

                field.selectAll();
            }
        });
        return field;
    }

    private JLabel label(String defaultValue, String mnemonic) {

        JLabel label = new JLabel(defaultValue);
        label.setToolTipText(mnemonic);
        label.setHorizontalAlignment(JLabel.RIGHT);
        return label;
    }

    private JButton scheduleButton() {

        JButton b = new JButton("Schedule");
        parent.getRootPane().setDefaultButton(b);
        b.addActionListener(e -> {
            
            if(CollectionUtils.isEmpty(matchList)) {
                populate();
            }
            else {
                b.setEnabled(false);
                submitted();
            }
        });
        return b;
    }
    
    private void populate() {
        
        try {
            matchList = scoreService.getCurrentMatches();
            
            if(CollectionUtils.isEmpty(matchList)) {
                setMessage("no ongoing matches");
            }
            else {
                matchList.forEach(m -> matchesCombo.addItem(getMatchItem(m)));
                setAllEnabled(true);
                setMessage("choose match for get popup notifcation");
            }
        }
        catch(Exception e) {
            LOG.warn(e.getMessage(), e);
            setMessage(e.getMessage());
        }
    }
    
    private void setAllEnabled(boolean enabled) {
        
//        if(matchesCombo.getItemCount() > 0) {
//            matchesCombo.setSelectedIndex(0);
//        }

        matchesCombo.setEnabled(enabled);
        popupTimeField.setEnabled(enabled);
        popupFreuencyField.setEnabled(enabled);
    }
    
    private void setMessage(String msg) {
        msgLabel.setText(msg);
    }
}
