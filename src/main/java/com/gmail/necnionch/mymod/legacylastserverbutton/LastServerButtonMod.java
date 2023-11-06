package com.gmail.necnionch.mymod.legacylastserverbutton;

import com.gmail.necnionch.mymod.legacylastserverbutton.lib.SimpleConfig;
import net.fabricmc.api.ClientModInitializer;
import net.legacyfabric.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.legacyfabric.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class LastServerButtonMod implements ClientModInitializer {
	private static @Nullable LastServerButtonMod instance;
	private final SimpleConfig config = SimpleConfig
			.of("lastserverbutton")
			.provider(filename -> "last-server-host=\nlast-server-port=")
			.request();

	public static void joinLastServer() {
		if (instance == null)
			return;

		String host = instance.getLastServerHost();
		int port = instance.getLastServerPort();
		if (host == null)
			return;

		if (port <= 0)
			port = 25565;

		MinecraftClient client = MinecraftClient.getInstance();
		client.setScreen(new ConnectScreen(client.currentScreen, client, host, port));
	}

	public static boolean hasLastServer() {
		return instance != null && instance.getLastServerHost() != null;
	}

	public static @Nullable LastServerButtonMod getInstance() {
		return instance;
	}

	@Override
	public void onInitializeClient() {
		instance = this;
		ClientPlayConnectionEvents.JOIN.register(this::onPlayReady);
	}


	public @Nullable String getLastServerHost() {
		String val = config.getOrDefault("last-server-host", null);
		return val.isEmpty() ? null : val;
	}

	public int getLastServerPort() {
		return config.getOrDefault("last-server-port", 0);
	}

	public void setLastServer(String host, int port) {
		config.set("last-server-host", host);
		config.set("last-server-port", port);
		try {
			config.saveConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void onPlayReady(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
		SocketAddress address = handler.getClientConnection().getAddress();
		if (address instanceof InetSocketAddress) {
			String hostName = ((InetSocketAddress) address).getHostName();
			int port = ((InetSocketAddress) address).getPort();

			setLastServer(hostName, port);
		}
	}

}
