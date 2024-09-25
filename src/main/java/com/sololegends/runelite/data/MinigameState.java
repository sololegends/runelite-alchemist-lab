package com.sololegends.runelite.data;

public class MinigameState {

	private static AlchemistOrder TARGET_ORDER;
	private static int MOX_POINTS = -1;
	private static int AGA_POINTS = -1;
	private static int LYE_POINTS = -1;

	public static void setOrder(AlchemistOrder order) {
		TARGET_ORDER = order;
	}

	public static AlchemistOrder getOrder() {
		return TARGET_ORDER;
	}

}
