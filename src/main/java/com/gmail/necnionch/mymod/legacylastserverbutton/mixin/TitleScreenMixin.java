package com.gmail.necnionch.mymod.legacylastserverbutton.mixin;

import com.gmail.necnionch.mymod.legacylastserverbutton.LastServerButtonMod;
import com.gmail.necnionch.mymod.legacylastserverbutton.widget.RunnableButtonWidget;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends ScreenMixin {
	@Inject(at = @At("TAIL"), method = "init")
	private void init(CallbackInfo ci) {
		if (LastServerButtonMod.hasLastServer())
			addLastServerButton();
	}

	@Inject(at = @At("TAIL"), method = "buttonClicked")
	private void buttonClicked(ButtonWidget button, CallbackInfo ci) {
		if (button instanceof RunnableButtonWidget) {
			((RunnableButtonWidget) button).doAction();
		}
	}

	private void addLastServerButton() {
		for (ButtonWidget button : buttons()) {
			if (button.id == 2) {  // multi play
				button.setWidth(98);

				// add quick button
				int x = this.width / 2 + 2;
				int y = button.y;
				String label = getLastServerLabel();

				RunnableButtonWidget quickButton = new RunnableButtonWidget(8000, x, y, label, LastServerButtonMod::joinLastServer);
				quickButton.setWidth(98);
				buttons().add(quickButton);

				break;
			}
		}
	}

	private String getLastServerLabel() {
		LastServerButtonMod instance = LastServerButtonMod.getInstance();
		if (instance != null) {
			String label = instance.getLastServerHost();
			if (label != null) {
				int port = instance.getLastServerPort();
				if (port != 25565)
					label += ":" + port;
				return label;
			}
		}
		return "";
	}

}
