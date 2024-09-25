package com.sololegends.runelite.data;

import java.awt.Color;

public class AlchemistObjects {

	// ==============================================
	// LEVERS
	// ==============================================
	public static final int MOX_LEVER = 54868;
	public static final Color MOX_LEVER_C = new Color(85, 69, 162);
	public static final int AGA_LEVER = 55393;
	public static final Color AGA_LEVER_C = new Color(67, 141, 59);
	public static final int LYE_LEVER = 54869;
	public static final Color LYE_LEVER_C = new Color(146, 66, 63);
	// ==============================================
	// HOPPER
	// ==============================================
	public static final int HOPPER = 54903;

	// ==============================================
	// MIXERS
	// ==============================================
	public static final int MIXER_1 = 55392;
	public static final int MIXER_2 = 55393;
	public static final int MIXER_3 = 55394;

	// ==============================================
	// STANDS
	// ==============================================
	public static final int CONCENTRATE_STAND = 55389;
	public static final int CRYSTALISE_STAND = 55391;
	public static final int HOMOGENISE_STAND = 55390;
	public static final int MIXING_VESSEL = 55395;

	public static final int CONVEYOR = 54917;

	public static Color getColorFromObj(int obj_id) {
		switch (obj_id) {
			case MOX_LEVER:
				return MOX_LEVER_C;
			case AGA_LEVER:
				return AGA_LEVER_C;
			case LYE_LEVER:
				return LYE_LEVER_C;
			default:
				return Color.GREEN;
		}
	}

}
