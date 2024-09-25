package com.sololegends.runelite;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.sololegends.runelite.data.AlchemistOrder;
import com.sololegends.runelite.data.Potion;

import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.ComponentID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.*;

public class AlchemistLabInventoryOverlay extends Overlay {

	private final Client client;
	private final AlchemistLabPlugin plugin;
	private final AlchemistLabConfig config;

	@Inject
	private AlchemistLabInventoryOverlay(Client client, AlchemistLabPlugin plugin,
			AlchemistLabConfig config) {
		super(plugin);
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(Overlay.PRIORITY_MED);
		this.client = client;
		this.config = config;
		this.plugin = plugin;
	}

	public void highlightThings(Graphics2D graphics) {
		Player player = client.getLocalPlayer();

		WorldPoint world = player.getWorldLocation();
		LocalPoint local = player.getLocalLocation();

		int region_id = world.getRegionID();
		if (plugin.inInstancedRegion()) {
			region_id = WorldPoint.fromLocalInstance(client, local).getRegionID();
		}
		// If not in the lab, return
		if (region_id != 5521) {
			return;
		}
		// Check for order potion in inventory
		Widget inventory = client.getWidget(ComponentID.INVENTORY_CONTAINER);
		Map<Potion, Widget> slots = new HashMap<>();
		for (Widget slot : inventory.getChildren()) {
			Potion pot = Potion.getPotion(slot.getItemId());
			if (pot != null) {
				boolean is_order = false;
				for (AlchemistOrder order : plugin.getOrders()) {
					if (order.potion.equals(pot)) {
						is_order = true;
					}
				}
				if (!is_order) {
					graphics.setColor(Color.RED);
					graphics.draw(slot.getBounds());
				}
				slots.put(pot, slot);
			}
			pot = Potion.getFinishedPotion(slot.getItemId());
			if (pot != null) {
				boolean is_order = false;
				for (AlchemistOrder order : plugin.getOrders()) {
					// If the potion in inventory is an order
					if (order.potion.equals(pot)) {
						// Render the conveyor
						is_order = true;
					}
				}
				if (!is_order) {
					graphics.setColor(Color.RED);
					graphics.draw(slot.getBounds());
				}
			}
		}
		for (AlchemistOrder order : plugin.getOrders()) {
			// Inventory has item
			if (slots.containsKey(order.potion)) {
				graphics.setColor(Color.CYAN);
				graphics.draw(slots.get(order.potion).getBounds());
			}
		}
		// END
	}

	@Override
	public Dimension render(Graphics2D graphics) {
		highlightThings(graphics);
		return null;
	}

}
