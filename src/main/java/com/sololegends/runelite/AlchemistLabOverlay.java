package com.sololegends.runelite;

import java.awt.*;
import java.util.*;

import com.google.inject.Inject;
import com.sololegends.runelite.data.*;

import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.ComponentID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.*;

public class AlchemistLabOverlay extends Overlay {

	private final Client client;
	private final AlchemistLabPlugin plugin;
	private final AlchemistLabConfig config;

	@Inject
	private AlchemistLabOverlay(Client client, AlchemistLabPlugin plugin,
			AlchemistLabConfig config) {
		super(plugin);
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
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
		int z = plugin.getPlane();
		Tile[][][] tiles = plugin.getScene().getTiles();

		// DETECT CURRENT POTION AND INVENTORY
		int i1 = client.getVarbitValue(AlchemistVarbits.MIXER_1);
		int i2 = client.getVarbitValue(AlchemistVarbits.MIXER_2);
		int i3 = client.getVarbitValue(AlchemistVarbits.MIXER_3);

		boolean is_mixing = client.getVarbitValue(AlchemistVarbits.CONCENTRATE_PROGRESS) > 0
				|| client.getVarbitValue(AlchemistVarbits.CRYSTALISE_PROGRESS) > 0
				|| client.getVarbitValue(AlchemistVarbits.HOMOGENISE_PROGRESS) > 0;

		// Check for order potion in inventory
		Widget inventory = client.getWidget(ComponentID.INVENTORY_CONTAINER);
		Map<Potion, Widget> slots = new HashMap<>();
		boolean render_conveyor = false;
		boolean order_completed = false;
		for (Widget slot : inventory.getChildren()) {
			Potion pot = Potion.getPotion(slot.getItemId());
			if (pot != null) {
				for (AlchemistOrder order : plugin.getOrders()) {
					// If the potion in inventory is an order
					if (order.potion.equals(pot)) {
						order_completed = true;
					}
				}
				slots.put(pot, slot);
			}
			pot = Potion.getFinishedPotion(slot.getItemId());
			if (pot != null) {
				for (AlchemistOrder order : plugin.getOrders()) {
					// If the potion in inventory is an order
					if (order.potion.equals(pot)) {
						// Render the conveyor
						render_conveyor = true;
						order_completed = true;
					}
				}
			}
		}
		Set<FinalTechnique> HIGHLIGHT_TECHNIQUES = new HashSet<>();
		AlchemistOrder best_match = MinigameState.getOrder();
		for (AlchemistOrder order : plugin.getOrders()) {
			// Inventory has item
			if (slots.containsKey(order.potion)) {
				HIGHLIGHT_TECHNIQUES.add(order.technique);
				best_match = order;
			}
			if (!order_completed) {
				// If no potions in inventory
				if (order.potion.matches(i1, i2, i3)) {
					best_match = order;
				}
				// Check for partial match
				if (best_match == null && order.potion.needs(i1, i2, i3).length < 3) {
					best_match = order;
				} else if (best_match != null &&
						order.potion.needs(i1, i2, i3).length < best_match.potion.needs(i1, i2, i3).length) {
					best_match = order;
				}
			}

			// Set color dots for orders
			int x1 = order.widget.getBounds().x + order.widget.getBounds().width;
			int y1 = order.widget.getBounds().y + order.widget.getBounds().height / 2;
			int size = (int) (order.widget.getBounds().height * 0.33);
			y1 -= size / 2;
			x1 += (size / 3);
			// Render ingredient #1
			graphics.setColor(PotionIngredient.fromId(order.potion.i1).color());
			graphics.fillOval(x1, y1, size, size);
			x1 += size + (size / 3);

			// Render ingredient #2
			graphics.setColor(PotionIngredient.fromId(order.potion.i2).color());
			graphics.fillOval(x1, y1, size, size);
			x1 += size + (size / 3);

			// Render ingredient #3
			graphics.setColor(PotionIngredient.fromId(order.potion.i3).color());
			graphics.fillOval(x1, y1, size, size);

		}

		// END
		// POTION ORDER NEEDS
		PotionIngredient[] needs = new PotionIngredient[0];
		if (best_match != null && !is_mixing) {
			MinigameState.setOrder(best_match);
			// We have a target order
			needs = best_match.potion.needs(i1, i2, i3);
			// Render current order
			graphics.setColor(Color.GREEN);
			graphics.draw(best_match.widget.getBounds());
		}
		// END

		graphics.setStroke(new BasicStroke(2));
		for (int x = 0; x < Constants.SCENE_SIZE; ++x) {
			for (int y = 0; y < Constants.SCENE_SIZE; ++y) {
				Tile tile = tiles[z][x][y];
				GameObject[] objs = tile.getGameObjects();
				if (objs != null) {
					for (GameObject obj : objs) {
						if (obj == null) {
							continue;
						}
						// If there are no pots in inventory AND obj is a level
						if (!is_mixing && !render_conveyor && slots.size() == 0 && needs.length > 0) {
							switch (needs[0]) {
								case AGA:
									if (obj.getId() == AlchemistObjects.AGA_LEVER) {
										graphics.setColor(AlchemistObjects.getColorFromObj(obj.getId()));
										graphics.draw(obj.getConvexHull());
									}
									break;
								case LYE:
									if (obj.getId() == AlchemistObjects.LYE_LEVER) {
										graphics.setColor(AlchemistObjects.getColorFromObj(obj.getId()));
										graphics.draw(obj.getConvexHull());
									}
									break;
								case MOX:
									if (obj.getId() == AlchemistObjects.MOX_LEVER) {
										graphics.setColor(AlchemistObjects.getColorFromObj(obj.getId()));
										graphics.draw(obj.getConvexHull());
									}
									break;
								case BLANK:
								default:
									break;
							}
						} else if (!is_mixing && slots.size() == 0 && needs.length == 0
								&& obj.getId() == AlchemistObjects.MIXING_VESSEL) {
							// If nothing in inventory and nothing is needed highlight the mixer
							graphics.setColor(AlchemistObjects.getColorFromObj(obj.getId()));
							graphics.draw(obj.getConvexHull());
						}

						// Highlight technique to be used
						if (HIGHLIGHT_TECHNIQUES.contains(FinalTechnique.CONCENTRATE)
								&& obj.getId() == AlchemistObjects.CONCENTRATE_STAND) {
							graphics.setColor(AlchemistObjects.getColorFromObj(obj.getId()));
							graphics.draw(obj.getConvexHull());
						}
						if (HIGHLIGHT_TECHNIQUES.contains(FinalTechnique.CRYSTALISE)
								&& obj.getId() == AlchemistObjects.CRYSTALISE_STAND) {
							graphics.setColor(AlchemistObjects.getColorFromObj(obj.getId()));
							graphics.draw(obj.getConvexHull());
						}
						if (HIGHLIGHT_TECHNIQUES.contains(FinalTechnique.HOMOGENISE)
								&& obj.getId() == AlchemistObjects.HOMOGENISE_STAND) {
							graphics.setColor(AlchemistObjects.getColorFromObj(obj.getId()));
							graphics.draw(obj.getConvexHull());
						}
						// CONVEYOR BELT
						if (render_conveyor && obj.getId() == AlchemistObjects.CONVEYOR) {
							graphics.setColor(AlchemistObjects.getColorFromObj(obj.getId()));
							graphics.draw(obj.getConvexHull());
						}
					}
				}
			}
		}
	}

	@Override
	public Dimension render(Graphics2D graphics) {
		highlightThings(graphics);
		return null;
	}

}
