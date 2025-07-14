package com.andrebinbow.overlay.dreamcatcher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.apache.logging.log4j.LogManager;


import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

@Mod(modid = DreamCatcher.MODID, version = DreamCatcher.VERSION)
public class DreamCatcher
{
    public static final String MODID = "dreamcatcher";
    public static final String VERSION = "1.0";
    public static String apikey = ""; // do not share
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static List<PlayerStats> lastParsedStats = new ArrayList<PlayerStats>();
    public static KeyBinding openGuiKey = new KeyBinding("Open DreamCatcher GUI", Keyboard.KEY_K, "DreamCatcher overlay");
    public static boolean guiOPEN = false;



    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new ExtractUsernames()); // Start listening to chat for /who results

        ClientRegistry.registerKeyBinding(openGuiKey);
        MinecraftForge.EVENT_BUS.register(this);

    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        try {
            if (openGuiKey.isPressed()) {
                Minecraft mc = Minecraft.getMinecraft();
                    if (!guiOPEN) {
                        mc.displayGuiScreen(new DreamCatcherGUI());
                        guiOPEN = true;
                    } else {
                        mc.thePlayer.closeScreen();
                        guiOPEN = false;
                    }
            }
        } catch(Exception e) {
            System.out.print(e);
        }
    }

}
