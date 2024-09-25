package com.sololegends.runelite;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.sololegends.runelite.data.*;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(name = "Alchemist Lab", description = "Helper for the Alchemist Lab minigame", tags = {
		"alchemist", "lab", "herblore", "varlamore", "aldaran" })
public class AlchemistLabPlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private AlchemistLabOverlay lab_overlay;

	@Inject
	private AlchemistLabInventoryOverlay lab_inv_overlay;

	@Inject
	private OverlayManager overlay_manager;

	@Override
	protected void startUp() throws Exception {
		log.info("Starting Alchemist Lab");
		overlay_manager.add(lab_overlay);
		overlay_manager.add(lab_inv_overlay);
	}

	@Override
	protected void shutDown() throws Exception {
		log.info("Stopping Alchemist Lab!");
		overlay_manager.remove(lab_overlay);
		overlay_manager.remove(lab_inv_overlay);
	}

	public Scene getScene() {
		WorldView wv = client.getTopLevelWorldView();
		return wv == null ? null : wv.getScene();
	}

	public int getPlane() {
		return client.getTopLevelWorldView().getPlane();
	}

	public boolean inInstancedRegion() {
		WorldView wv = client.getTopLevelWorldView();
		return wv == null ? false : wv.isInstance();
	}

	@Subscribe
	public void onChatMessage(ChatMessage event) {
	}

	private AlchemistOrder[] ORDERS;

	public AlchemistOrder[] getOrders() {
		return ORDERS;
	}

	@Subscribe
	public void onGameTick(GameTick event) {
		ORDERS = processOrders();
		if (ORDERS.length > 0) {
			MinigameState.setOrder(ORDERS[0]);
		}
	}

	public AlchemistOrder[] processOrders() {
		// Get the orders widget
		int ORDERS_WIDGET_ID = 57802754;
		Widget orders_widget = client.getWidget(ORDERS_WIDGET_ID);
		if (orders_widget != null) {
			Widget o1_tw = orders_widget.getChild(1);
			FinalTechnique o1_t = FinalTechnique.fromSpriteID(o1_tw.getSpriteId());
			Widget o1_pw = orders_widget.getChild(2);
			Potion o1_p = Potion.fromName(o1_pw.getText());

			Widget o2_tw = orders_widget.getChild(3);
			FinalTechnique o2_t = FinalTechnique.fromSpriteID(o2_tw.getSpriteId());
			Widget o2_pw = orders_widget.getChild(4);
			Potion o2_p = Potion.fromName(o2_pw.getText());

			Widget o3_tw = orders_widget.getChild(5);
			FinalTechnique o3_t = FinalTechnique.fromSpriteID(o3_tw.getSpriteId());
			Widget o3_pw = orders_widget.getChild(6);
			Potion o3_p = Potion.fromName(o3_pw.getText());

			if (o1_t == null || o1_p == null
					|| o2_t == null || o2_p == null
					|| o3_t == null || o3_p == null) {
				return new AlchemistOrder[] {};
			}
			return new AlchemistOrder[] {
					new AlchemistOrder(o1_p, o1_t, o1_pw),
					new AlchemistOrder(o2_p, o2_t, o2_pw),
					new AlchemistOrder(o3_p, o3_t, o3_pw)
			};
		}
		return new AlchemistOrder[] {};
	}

	@Provides
	AlchemistLabConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(AlchemistLabConfig.class);
	}
}
