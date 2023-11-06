package com.gmail.necnionch.mymod.legacylastserverbutton.widget;

import net.minecraft.client.gui.widget.ButtonWidget;

public class RunnableButtonWidget extends ButtonWidget {

    private final Runnable clicked;

    public RunnableButtonWidget(int id, int x, int y, String message, Runnable clicked) {
        super(id, x, y, message);
        this.clicked = clicked;
    }

    public RunnableButtonWidget(int id, int x, int y, int width, int height, String message, Runnable clicked) {
        super(id, x, y, width, height, message);
        this.clicked = clicked;
    }

    public void doAction() {
        clicked.run();
    }

}
