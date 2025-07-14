package com.andrebinbow.overlay.dreamcatcher;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class ApiKeyGUI extends GuiScreen {
    private GuiButton saveButton;
    private GuiTextField apiKeyTextField;
    private int apiKeyTextFieldX, apiKeyTextFieldY;
    private GuiButton backButton;
    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true); //Allows you to hold down backspace to delete
        apiKeyTextFieldX = this.width/2 - 100;
        apiKeyTextFieldY = this.height-100;
        apiKeyTextField = new GuiTextField(0, this.fontRendererObj, apiKeyTextFieldX, apiKeyTextFieldY, 200, 20);

        saveButton = new GuiButton(1, this.width / 2, this.height-75, 100, 20, "Save and return");
        this.buttonList.add(saveButton);

        backButton = new GuiButton(2, this.width / 2-100, this.height-75, 100, 20, "Return");
        this.buttonList.add(backButton);

        apiKeyTextField.setMaxStringLength(64); // Default (32) is too small to contain API key (36 characters). This took an embarrassing amount of time to figure out
        apiKeyTextField.setText(DreamCatcher.apikey);
        apiKeyTextField.setFocused(true);
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Enter API Key:", this.width /2, apiKeyTextFieldY - 12, 0xFFFFFF);
        apiKeyTextField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        int mouseX = Mouse.getX();
        int mouseY = Mouse.getY();
        if (button == saveButton) {
            DreamCatcher.apikey = apiKeyTextField.getText();
            mc.thePlayer.closeScreen();
            mc.displayGuiScreen(new DreamCatcherGUI());
        }
        if (button == backButton) {
            mc.thePlayer.closeScreen();
            mc.displayGuiScreen(new DreamCatcherGUI());
        }
        Mouse.setCursorPosition(mouseX, mouseY); // GUI screen normally sets cursor to middle of GUI,this is stupid fix but it works
    }
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        apiKeyTextField.textboxKeyTyped(typedChar, keyCode);
        //apiKeyTextField.getVisible();
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        apiKeyTextField.mouseClicked(mouseX, mouseY, mouseButton);
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
