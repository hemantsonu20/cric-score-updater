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

import java.net.URI;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
@ConditionalOnProperty(value = "cric.api.enabled", havingValue = "true")
public class CricApiConfiguration implements InitializingBean {

    private static final String API_KEY = "apikey";

    @Value("cric.api.key")
    private String apiKey;

    /**
     * This interceptor adds cric-api-key to all outgoing request
     * 
     * @return
     */
    @Bean
    public ClientHttpRequestInterceptor outgoingCricApiRequestInterceptor() {

        return (request, body, execution) -> {

            HttpRequest modified = new HttpRequestWrapper(request) {

                @Override
                public URI getURI() {

                    return UriComponentsBuilder.fromUri(super.getURI()).queryParam(API_KEY, apiKey).build().toUri();
                }
            };
            return execution.execute(modified, body);
        };
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        Assert.notNull(apiKey, "define an env \"cric.api.key\"={cric api key}");
    }
}
