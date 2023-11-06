package com.gmail.necnionch.mymod.legacylastserverbutton.mixin;

import com.gmail.necnionch.mymod.legacylastserverbutton.accessor.ScreenAccessor;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(Screen.class)
public class ScreenMixin implements ScreenAccessor {
    @Shadow
    protected List<ButtonWidget> buttons;

    @Shadow public int width;

    @Override
    public List<ButtonWidget> buttons() {
        return buttons;
    }
}
