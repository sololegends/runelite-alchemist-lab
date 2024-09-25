package com.sololegends.runelite.data;

public class AlchemistVarbits {

	public static final int MIXER_1 = 11324;
	public static final int MIXER_2 = 11325;
	public static final int MIXER_3 = 11326;

	public static final int CONCENTRATE_PROGRESS = 11327;
	public static final int CRYSTALISE_PROGRESS = 11328;
	public static final int HOMOGENISE_PROGRESS = 11329;

	public static final int VALUE_MOX = 1;
	public static final int VALUE_AGA = 2;
	public static final int VALUE_LYE = 3;

	public static final int MIXING_VESSEL = 11339;

	// ==============================================
	// MIXING STATES
	// ==============================================
	public static final int STATE_MIXING_VESSEL_MIXALOT = 10;

	public static Potion getPotion(int mixer_contents) {
		for (Potion pot : Potion.values()) {
			if (pot.id == mixer_contents) {
				return pot;
			}
		}
		return null;
	}

}
