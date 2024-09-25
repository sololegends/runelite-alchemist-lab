package com.sololegends.runelite.data;

import java.awt.Color;

public enum PotionIngredient {
	BLANK, MOX, AGA, LYE;

	public Color color() {
		switch (this) {
			case AGA:
				return AlchemistObjects.AGA_LEVER_C;
			case LYE:
				return AlchemistObjects.LYE_LEVER_C;
			case MOX:
				return AlchemistObjects.MOX_LEVER_C;
			case BLANK:
			default:
				return Color.GRAY;

		}
	}

	public static PotionIngredient fromId(int id) {
		if (id > 0 && id < 4) {
			return PotionIngredient.values()[id];
		}
		return PotionIngredient.BLANK;
	}
}
