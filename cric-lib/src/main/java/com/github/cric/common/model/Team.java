package com.github.cric.common.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Team {

    INDIA("IND", "INDIA"), AUSTRALIA("AUS", "AUSTRALIA");

    private final String shortName;
    private final String fullName;

    private Team(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public String getShortName() {

        return shortName;
    }

    public String getFullName() {

        return fullName;
    }

    @JsonCreator
    public static Team fromName(String name) {

        return LOOKUP_MAP.get(name);
    }

    /**
     * Faster search for team enum from team names (full or short)
     * 
     */
    private static Map<String, Team> LOOKUP_MAP = new HashMap<>();
    static {
        EnumSet.allOf(Team.class).forEach(e -> {
            LOOKUP_MAP.put(e.getShortName(), e);
            LOOKUP_MAP.put(e.getFullName(), e);
        });
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this).append(shortName).append(fullName).build();
    }
}
