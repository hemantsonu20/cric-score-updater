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

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.github.cric.common.EnableCommonCricLib;
import com.github.cric.common.EnableCricApiDotCom;
import com.github.cric.common.listener.CricContext;
import com.github.cric.common.listener.SummaryScoreResponse;
import com.github.cric.common.service.ScoreService;

@SpringBootApplication
@EnableCommonCricLib
@EnableCricApiDotCom
public class UiApplication implements CommandLineRunner {

    public static void main(String[] args) {

        new SpringApplicationBuilder(UiApplication.class).headless(false).web(false).run(args);
    }

    private static final int DEFAULT_POPUP_TIME = 10;
    
    @Autowired
    private CricContext context;

    @Autowired
    private ScoreService service;

    @Autowired
    private NotificationWindow popup;
    
    private CountDownLatch latch = new CountDownLatch(1);
    
    // msgs to display
    private String heading;
    private String msg;

    @Override
    public void run(String... args) throws Exception {

        // List<Match> m = service.getCurrentMatches("INDIA");

        popup.showMsg("heading", "first");
        context.registerSummaryScoreListener(this::consume, 1003769, 20);
        
        while(true) {
            
            latch.await();
            System.out.println(msg);
            popup.showMsg(heading, msg);
            latch = new CountDownLatch(1);
            Thread.sleep(DEFAULT_POPUP_TIME * 1000);
            popup.setVisible(false);
        }
    }
    

    private void consume(SummaryScoreResponse s) {
        
        if(s.hasError()) {
            this.heading = "Error";
            this.msg =s.getError().getMessage();
        }
        else {
            this.heading = "heading";
            this.msg = s.getSummaryScore().getScore();;
        }
        latch.countDown();
    }
}
