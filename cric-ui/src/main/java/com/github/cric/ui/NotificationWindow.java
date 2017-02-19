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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.springframework.stereotype.Component;

@Component
public class NotificationWindow extends JDialog {

    private static final long serialVersionUID = 1L;

    private static final String HTML_FORMAT = "<html>%s</html>";

    private final JLabel messageLabel = new JLabel("(00.0 ov, xxxxx 00, xxxxx 00)");
    private final JLabel requirementLabel = new JLabel("000 run required to win");

    private final GraphicsDevice graphicsDevice = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice();

    static {
        JDialog.setDefaultLookAndFeelDecorated(true);
    }

    public NotificationWindow() {

        super();
        setLayout(new BorderLayout());
        setSize(400, 110);
        setLocationRelativeTo(null);
        if (isTranslucencySupported()) {
            setOpacity(0.80f);
        }
        setAlwaysOnTop(true);
        setResizable(false);
        
        add(messageLabel, BorderLayout.NORTH);
        add(requirementLabel, BorderLayout.CENTER);
        messageLabel.setForeground(Color.DARK_GRAY);
        requirementLabel.setForeground(Color.DARK_GRAY);
        
        setLocationAtRightBottom();
    }

    public void showMsg(PopupMessage popupMessage) {

        setMessage(popupMessage);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                NotificationWindow.this.setVisible(true);
            }
        });
    }

    private void setMessage(PopupMessage popupMessage) {

        System.out.println(popupMessage);
        setTitle(popupMessage.getHeading());
        messageLabel.setText(String.format(HTML_FORMAT, popupMessage.getMessage()));
        requirementLabel.setText(String.format(HTML_FORMAT, popupMessage.getRequirement()));
    }

    private boolean isTranslucencySupported() {

        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

        return graphicsDevice.isWindowTranslucencySupported(WindowTranslucency.TRANSLUCENT);
    }
    
    private void setLocationAtRightBottom() {

        Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(
                NotificationWindow.this.getGraphicsConfiguration());
        int taskBarSize = scnMax.bottom;
        Rectangle rect = graphicsDevice.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - NotificationWindow.this.getWidth();
        int y = (int) rect.getMaxY() - NotificationWindow.this.getHeight() - taskBarSize;
        this.setLocation(x, y);
    }
}
