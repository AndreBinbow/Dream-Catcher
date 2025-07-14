package com.andrebinbow.overlay.dreamcatcher;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExtractUsernames {
    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {

        String message = event.message.getUnformattedText();
        System.out.println("RECEIVED MESAGE: "+message);

        if (message.contains("ONLINE") && message.contains(":")) {
            if (DreamCatcher.apikey == null || DreamCatcher.apikey.trim().isEmpty()) {
                // Send message to player saying API key missing
                Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("§cError: No API key set! Please enter your API key in settings."));
                return; // skip processing because no API key
            }
            final List<String> USERNAMES = extractUsernames(message);
            final List<PlayerStats>[] parsedHolder = new List[] { new ArrayList<PlayerStats>() }; // Threads don't like accessing non-final variables, this is a strange hack
            new Thread(() -> {
                    for (String name : USERNAMES) {
                        try {
                    if (WhitelistHandler.getWhiteList().contains(name)) {
                        continue;
                    }
                            String response = ApiCaller.callApi("https://api.hypixel.net/player?name=" + name + "&key=" + DreamCatcher.apikey);
                            PlayerStats stats = JsonReader.parseJson(response);

                        parsedHolder[0].add(stats);
                        } catch (Exception e) {
                            System.out.println("Error retrieving stats for " + name + ": " + e.getMessage());
                        }
                    }

            }).start(); // If not threaded, running on main thread causes mineraft to freeze while the API is called.

            // Store result for GUI access
            DreamCatcher.lastParsedStats.clear();
            DreamCatcher.lastParsedStats = parsedHolder[0];
            System.out.println(DreamCatcher.lastParsedStats);
        }
    }

    // Parses usernames out of a /who message
    // Assuming input= "ONLINE: player1, player2, player3..."
    private List<String> extractUsernames(String message) {
        try {
            int colonIndex = message.indexOf(":");
            if (colonIndex == -1) return new ArrayList<String>();

            String userList = message.substring(colonIndex + 1).trim();
            return Arrays.asList(userList.split(",\\s*"));
        } catch (Exception e) {
            System.out.println("Failed to parse usernames: " + e.getMessage());
            return new ArrayList<String>();
        }
    }
}
