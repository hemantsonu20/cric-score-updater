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

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.cric.common.config.CustomResponseErrorHandler;

@Configuration
@ComponentScan(basePackageClasses = CricApiConfiguration.class)
public class CricApiConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(CricApiConfiguration.class);

    private static final String API_KEY = "apikey";

    @Value("${cric.api.key}")
    private String apiKey;

    @Bean
    public RestTemplate cricApiRestTemplate() {

        RestTemplate template = new RestTemplate();
        template.setErrorHandler(new CustomResponseErrorHandler());
        template.getInterceptors().add(outgoingCricApiRequestLoggerInterceptor());
        template.getInterceptors().add(outgoingCricApiRequestInterceptor());
        return template;
    }

    /**
     * This interceptor adds cric-api-key to all outgoing request.
     */
    private ClientHttpRequestInterceptor outgoingCricApiRequestInterceptor() {

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

    private ClientHttpRequestInterceptor outgoingCricApiRequestLoggerInterceptor() {

        return (request, body, execution) -> {

            String requestUrl = request.getURI().toString();
            LOG.debug("sending request to {}", requestUrl);
            
            StopWatch watch = new StopWatch();
            watch.start();
            ClientHttpResponse clientResponse = execution.execute(request, body);
            watch.stop();
            
            LOG.debug(
                    "response received from {} status {} time taken {}",
                    requestUrl,
                    clientResponse.getStatusCode(),
                    watch.getTime());
            return clientResponse;
        };
    }
}
