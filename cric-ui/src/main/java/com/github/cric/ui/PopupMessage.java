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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PopupMessage {

    private String heading;
    private String message;
    private String requirement;

    public String getHeading() {

        return heading;
    }

    public String getMessage() {

        return message;
    }

    public String getRequirement() {

        return requirement;
    }

    public PopupMessage setHeading(String heading) {

        this.heading = heading;
        return this;
    }

    public PopupMessage setMessage(String message) {

        this.message = message;
        return this;
    }

    public PopupMessage setRequirement(String requirement) {

        this.requirement = requirement;
        return this;
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("heading", heading)
                .append("message", message)
                .append("requirement", requirement)
                .build();
    }
}
