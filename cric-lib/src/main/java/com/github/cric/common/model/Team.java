package com.github.cric.common.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Team {

    IND("IND", "INDIA"), SL("SL", "SRI LANKA"), PAK("PAK", "PAKISTAN"), BAN("BAN", "BANGLADESH"), AUS("AUS",
            "AUSTRALIA"), NZ("NZ", "NEW ZEALAND"), ZIM("ZIM", "ZIMBABWE"), ENG("ENG", "ENGLAND"), WI("WI",
            "WEST INDIES"), SA("SA", "SOUTH AFRICA"), UNKNOWN("UNKNOWN", "UNKNOWN");

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

        Team t = LOOKUP_MAP.get(name.toUpperCase());
        if (null != t) {
            return t;
        }
        else {
            return UNKNOWN;
        }
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

        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(shortName).append(fullName).build();
    }
}
