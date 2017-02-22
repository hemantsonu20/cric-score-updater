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
package com.github.cric.common.service.cripapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.cric.common.listener.CricContext;
import com.github.cric.common.listener.SummaryScoreListener;
import com.github.cric.common.listener.SummaryScoreResponse;
import com.github.cric.common.service.ScoreService;

@Component
public class CricApiContext implements CricContext, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(CricApiContext.class);
    
    public static final int DEFAULT_INITIAL_POPUP_DELAY = 10;
    
    private static final Map<SummaryScoreListener, ScheduledFuture<?>> FUTURE = new HashMap<>();
    private static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(1);

    @Autowired
    private ScoreService scoreService;

    /**
     * Register a SummaryScoreListener which will be scheduled to execute.
     * 
     */
    @Override
    public void registerSummaryScoreListener(SummaryScoreListener s, int matchId, int seconds) {

        
        
        Objects.requireNonNull(s);
        ScheduledFuture<?> f = EXECUTOR.scheduleWithFixedDelay(
                getTask(s, matchId),
                DEFAULT_INITIAL_POPUP_DELAY,
                seconds,
                TimeUnit.SECONDS);
        FUTURE.put(s, f);
    }

    @Override
    public void unregisterSummaryScoreListener(SummaryScoreListener s) {

        if(null == s) {
            return;
        }
        
        ScheduledFuture<?> f = FUTURE.get(s);
        if (null != f) {
            f.cancel(true);
        }
    }

    private Runnable getTask(SummaryScoreListener s, int matchId) {

        return () -> {

            SummaryScoreResponse r;
            try {
                r = new SummaryScoreResponse(scoreService.getSummaryScore(matchId));
            } catch (Exception e) {
                r = new SummaryScoreResponse(e);
            }
            try {
                s.updateSummaryScore(r);
            } catch (Exception e) {

                LOG.debug("ignoring exception from listener", e);
            }
        };
    }

    @Override
    public void destroy() throws Exception {

        close();
    }

    @Override
    public void close() {

        EXECUTOR.shutdown();
    }
}
