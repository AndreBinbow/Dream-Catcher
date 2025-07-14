package com.andrebinbow.overlay.dreamcatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class WhitelistHandler {
    private static ArrayList<String> whitelist = new ArrayList<String>(
            Arrays.asList(
                    "Thermodynamic",
                    "MyBedtime",
                    "Hipeople21"
            )
    ); // May be more useful if it could dynamically detect teammates

    public static ArrayList<String> getWhiteList() {
        return whitelist;
    }

    private static boolean containsIgnoreCase(String username) {
        return whitelist.stream().anyMatch(name -> name.equalsIgnoreCase(username)); // Fancy lambda expression to ignore case
    }

    public static boolean addUserToWhitelist(String username) {
        if (!containsIgnoreCase(username)) {
            whitelist.add(username);
            return true;
        }
        return false;
    }

    public static boolean removeUserFromWhitelist(String username) {
        for (int i = 0; i < whitelist.size(); i++) {
            if (whitelist.get(i).equalsIgnoreCase(username)) {
                whitelist.remove(i);
                return true;
            }
        }
        return false;
    }


}
