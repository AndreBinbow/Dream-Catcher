package com.andrebinbow.overlay.dreamcatcher;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class WhitelistHandlerGUI extends GuiScreen {
    private GuiTextField inputTextField;
    private int inputTextFieldX;
    private int inputTextFieldY;
    private GuiButton addButton;
    private GuiButton removeButton;
    private String statusMessage = "";
    private GuiButton backButton;

    public void drawWhitelist() {
        int y = 66;
        int centerX = this.width / 2;
        for (String username : WhitelistHandler.getWhiteList()) {
            this.drawCenteredString(this.fontRendererObj, username, centerX, y, 0xFFFFFF);
            y += 16;
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        inputTextFieldX = this.width / 2 - 100;
        inputTextFieldY = this.height - 100;
        inputTextField = new GuiTextField(0, this.fontRendererObj, inputTextFieldX, inputTextFieldY, 200, 20);
        Keyboard.enableRepeatEvents(true);
        inputTextField.setFocused(true);

        addButton = new GuiButton(1, this.width / 2, this.height - 60, 100, 20, "Add");
        this.buttonList.add(addButton);

        removeButton = new GuiButton(2, this.width / 2-100, this.height - 60, 100, 20, "Remove");
        this.buttonList.add(removeButton);

        backButton = new GuiButton(3, this.width/2-100, this.height-40, 200, 20, "Back");
        this.buttonList.add(backButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int centerX = this.width / 2;
        int y = 50;

        if (!WhitelistHandler.getWhiteList().isEmpty()) {
            this.drawCenteredString(this.fontRendererObj, "List of whitelisted users:", centerX, y, 0xFFFFFF);
            y+=16;
            drawWhitelist();

        } else {
            this.drawCenteredString(this.fontRendererObj, "Whitelist is empty! Enter a name below to add to whitelist!", centerX, y, 0xFFFFFF);
        }

        this.drawCenteredString(this.fontRendererObj, "Enter username:", this.width / 2, (this.height - 100) - 12, 0xFFFFFF);
        inputTextField.drawTextBox();
        if (!statusMessage.isEmpty()) {
            this.drawCenteredString(this.fontRendererObj, statusMessage, this.width / 2, this.height - 75, 0xFF5555);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        int mouseX = Mouse.getX();
        int mouseY = Mouse.getY();
        if (button == addButton) {
            String name = inputTextField.getText();
            boolean added = WhitelistHandler.addUserToWhitelist(name);
            WhitelistHandler.addUserToWhitelist(name);
            statusMessage = added ? name + " added to whitelist." : name + " is already whitelisted.";
            inputTextField.setText("");
            drawWhitelist();
        }
        if (button == removeButton) {
            String name = inputTextField.getText();
            boolean removed = WhitelistHandler.removeUserFromWhitelist(name);
            statusMessage = removed ? name + " removed from whitelist." : name + " not found in whitelist.";
            inputTextField.setText("");
            drawWhitelist();
        } if (button == backButton) {
            mc.displayGuiScreen(new DreamCatcherGUI());
        }
        Mouse.setCursorPosition(mouseX, mouseY); // GUI screen normally sets cursor to middle of GUI,this is stupid fix but it works
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        inputTextField.textboxKeyTyped(typedChar, keyCode);
        inputTextField.getVisible();
        super.keyTyped(typedChar, keyCode);
    }


    @Override
    public void onGuiClosed() {
            DreamCatcher.guiOPEN = false;
            super.onGuiClosed();
    }
    @Override
    public boolean doesGuiPauseGame () {
        return false;
    }


}
