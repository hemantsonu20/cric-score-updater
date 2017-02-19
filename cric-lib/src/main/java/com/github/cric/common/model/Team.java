package com.github.cric.common.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Team {

    private static final Logger LOG = LoggerFactory.getLogger(Team.class);

    /*********************** ASIAN TEAMS *************************************/

    public static final Team IND = new Team("IND", "INDIA", null);
    public static final Team SL = new Team("SL", "SRI LANKA", null);
    public static final Team PAK = new Team("PAK", "PAKISTAN", null);
    public static final Team BAN = new Team("BAN", "BANGLADESH", null);

    /********************* NON ASIAN TEAMS ***********************************/

    public static final Team AUS = new Team("AUS", "AUSTRALIA", null);
    public static final Team NZ = new Team("NZ", "NEW ZEALAND", null);
    public static final Team ZIM = new Team("ZIM", "ZIMBABWE", null);
    public static final Team ENG = new Team("ENG", "ENGLAND", null);
    public static final Team WI = new Team("WI", "WEST INDIES", null);
    public static final Team SA = new Team("SA", "SOUTH AFRICA", null);

    /*********************** NEW TEAMS ***************************************/

    public static final Team AFG = new Team("AFG", "AFGHANISTAN", null);

    /*************************************************************************/

    private final String shortName;
    private final String fullName;
    private final String rawName;

    private Team(String shortName, String fullName, String rawName) {

        this.shortName = shortName;
        this.fullName = fullName;
        this.rawName = rawName;
    }

    public String getShortName() {

        return shortName;
    }

    public String getFullName() {

        return fullName;
    }

    public String getRawName() {

        return rawName;
    }

    /**
     * Returns short name or raw name
     * 
     * @return
     */
    public String getDisplayName() {

        if (null != shortName) {
            return shortName;
        }
        else {
            return StringUtils.abbreviate(rawName, 15) ;
        }
    }

    @JsonCreator
    public static Team fromName(String name) {

        Team t = LOOKUP_MAP.get(name.toUpperCase());
        if (null != t) {
            return t;
        }
        else {
            return new Team(null, null, name);
        }
    }

    /**
     * Faster search for team enum team names (full or short)
     * 
     */
    private static Map<String, Team> LOOKUP_MAP = new HashMap<>();
    static {
        for (Field f : Team.class.getDeclaredFields()) {
            
            if (f.getType().equals(Team.class) && Modifier.isStatic(f.getModifiers())) {

                try {
                    Team t = (Team) f.get(null);
                    LOOKUP_MAP.put(t.shortName, t);
                    LOOKUP_MAP.put(t.fullName, t);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    LOG.trace(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(shortName)
                .append(fullName)
                .append(rawName)
                .build();
    }
}
