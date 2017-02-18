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

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.cric.common.EnableCommonCricLib;
import com.github.cric.common.EnableCricApiDotCom;
import com.github.cric.common.model.Match;
import com.github.cric.common.model.SummaryScore;
import com.github.cric.common.service.ScoreService;

@SpringBootApplication
@EnableCommonCricLib
@EnableCricApiDotCom
public class UiApplication implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(UiApplication.class, args).close();
    }

    @Autowired
    private ScoreService service;

    @Override
    public void run(String... args) throws Exception {

        String s = service
                .getCurrentMatches("INDIA")
                .stream()
                .mapToInt(Match::getMatchId)
                .mapToObj(service::getSummaryScore)
                .map(SummaryScore::toString)
                .collect(Collectors.joining());

        System.out.println(s);
    }
    
    
}
