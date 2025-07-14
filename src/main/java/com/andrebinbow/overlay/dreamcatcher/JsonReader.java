package com.andrebinbow.overlay.dreamcatcher;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class JsonReader {
    public static PlayerStats parseJson(String jsonRaw) {
        // Uses older version of Gson to avoid downloading newer dependency on top of Minecraft's already provided json parser.
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonRaw);
        JsonObject object = element.getAsJsonObject();

        boolean success = object.get("success").getAsBoolean();
        if (!success) {
            System.out.println(object.getAsString());
            return null;
        }
        System.out.println("API CALL SUCCEEDED");
        JsonObject player = object.getAsJsonObject("player");
        String username = player.get("displayname").getAsString();

        JsonObject stats = player.getAsJsonObject("stats");

        JsonObject bedwarsStats = stats.getAsJsonObject("Bedwars");
        int star = player.getAsJsonObject("achievements").get("bedwars_level").getAsInt(); // Accurate bedwars level is stored in the API (?)
        System.out.println("Bedwars level: "+star);
        int finalKills = bedwarsStats.get("final_kills_bedwars").getAsInt();
        int finalDeaths = bedwarsStats.get("final_deaths_bedwars").getAsInt();
        double fkdr;
        if (finalDeaths == 0) {
            fkdr = Double.POSITIVE_INFINITY;
        } else {
            fkdr = (double) finalKills/finalDeaths;
        }
        System.out.println(String.format("Username: %s, FKDR: %.2f, Star: %d", username, fkdr, star));
        return new PlayerStats(username, fkdr, star);
    }


}
