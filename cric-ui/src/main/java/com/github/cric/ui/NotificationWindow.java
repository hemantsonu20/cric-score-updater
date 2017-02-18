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

import java.awt.GraphicsDevice;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
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

    private final JLabel messageLabel = new JLabel("gdfgfdhfhfhfghfhfhfhf");

    private final GraphicsDevice graphicsDevice = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice();

    static {
        JDialog.setDefaultLookAndFeelDecorated(true);
    }

    public NotificationWindow() {

        super();
        setLayout(new GridBagLayout());
        setSize(300, 100);
        setLocationRelativeTo(null);
        if (isTranslucencySupported()) {
            setOpacity(0.80f);
        }
        setAlwaysOnTop(true);
        setResizable(false);
        add(messageLabel);
    }

    public void showMsg(String heading, String message) {

        setMessage(heading, message);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(
                        NotificationWindow.this.getGraphicsConfiguration());
                int taskBarSize = scnMax.bottom;
                Rectangle rect = graphicsDevice.getDefaultConfiguration().getBounds();
                int x = (int) rect.getMaxX() - NotificationWindow.this.getWidth();
                int y = (int) rect.getMaxY() - NotificationWindow.this.getHeight() - taskBarSize;
                NotificationWindow.this.setLocation(x, y);
                NotificationWindow.this.setVisible(true);
            }
        });
    }

    private void setMessage(String heading, String message) {

        messageLabel.setText(String.format("<html>%s %s</html>", heading, message));
    }

    private boolean isTranslucencySupported() {

        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

        return graphicsDevice.isWindowTranslucencySupported(WindowTranslucency.TRANSLUCENT);
    }
}
