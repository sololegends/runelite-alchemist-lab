package com.sololegends.runelite;

import net.runelite.client.config.*;

@ConfigGroup("Alchemist Lab")
public interface AlchemistLabConfig extends Config {

	//@formatter:off
	@ConfigSection(
		name = "Features", 
		description = "turn on and off features", 
		position = 0
	)

	String feature_section = "features";

	@ConfigItem(position = 1, 
		section = feature_section, 
		keyName = "enable_ingredient_dots", 
		name = "Ingredient Dots", 
		description = "Enable Overlay showing the ingredient types for the available orders"
	)
	default boolean enableDistractedOverlay() {
		return true;
	}

}
