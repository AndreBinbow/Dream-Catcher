package com.andrebinbow.overlay.dreamcatcher;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class DreamCatcherGUI extends GuiScreen {
    private static final ResourceLocation LOGO = new ResourceLocation("dreamcatcher", "textures/gui/logo.png");
    private static final ResourceLocation TITLE = new ResourceLocation("dreamcatcher", "textures/gui/title.png");
    private GuiButton closeButton;
    private GuiButton openAPISettings;
    private GuiButton openWhitelistSettings;
    @Override
    public void initGui() {
        super.initGui();

        closeButton = new GuiButton(0, this.width / 2 - 100, this.height - (this.height / 4) + 30, "Close");
        this.buttonList.add(closeButton);

        openAPISettings = new GuiButton(1, this.width / 2 - 150, this.height - (this.height / 4)+10, 150, 20, "API Key Settings");
        this.buttonList.add(openAPISettings);

        openWhitelistSettings = new GuiButton(2, this.width / 2,this.height - (this.height / 4)+10, 150, 20, "Open Player Settings");
        this.buttonList.add(openWhitelistSettings);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {

        int mouseX = Mouse.getX();
        int mouseY = Mouse.getY();
        if (button == closeButton) {
            mc.thePlayer.closeScreen();
            DreamCatcher.guiOPEN = false;
        }
        if (button == openAPISettings) {
            mc.displayGuiScreen(new ApiKeyGUI());
        }
        if (button == openWhitelistSettings) {
            mc.displayGuiScreen(new WhitelistHandlerGUI());
        }
        Mouse.setCursorPosition(mouseX, mouseY);
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        mc.getTextureManager().bindTexture(LOGO);
        drawModalRectWithCustomSizedTexture(25, 10, 0, 0, 50, 50, 50, 50);
        mc.getTextureManager().bindTexture(TITLE);
        drawModalRectWithCustomSizedTexture(100, 10, 0, 0, this.width - 200, 60, this.width - 150, 80);

        int y = 75; // vertical start pos
        int centerX = this.width / 2;
        this.drawCenteredString(this.fontRendererObj, "Made by", this.width - 75, 50, 0xFFFFFF);
        this.drawCenteredString(this.fontRendererObj, "Andre Binbow", this.width - 75, 50+this.fontRendererObj.FONT_HEIGHT, 0xFFFFFF); // Centered string doesn't recognise \n

        if (!DreamCatcher.lastParsedStats.isEmpty()) {
            for (PlayerStats stats : DreamCatcher.lastParsedStats) {
                String statLine = String.format("%s - FKDR: %.2f - Star: %d", stats.username, stats.fkdr, stats.star);
                int color = 0xFFFFFF; // white
                if (stats.fkdr >= 1.0 || stats.star >= 50) {
                    color = 0x6AFF45; // green, has played before
                }
                if (stats.fkdr >= 2.0 || stats.star >= 100) {
                    color = 0xE1EC0b; // yellow, mildly threatening
                }
                if (stats.fkdr >= 5.0 || stats.star >= 200) {
                    color = 0xFF1414; // red, danger
                }
                this.drawCenteredString(this.fontRendererObj, statLine, centerX, y, color);
                y += 12; // increment y by face size + padding
            }
        } else {
            this.drawCenteredString(this.fontRendererObj, "Please run /who during a bedwars game", centerX, y, 0xFFFFFF); // could add a "loading" state
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    @Override
    public void onGuiClosed() {
        DreamCatcher.guiOPEN = false;
        super.onGuiClosed();
    }



    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
