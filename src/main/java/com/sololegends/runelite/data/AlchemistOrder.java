package com.sololegends.runelite.data;

import net.runelite.api.widgets.Widget;

public class AlchemistOrder {
	public final Potion potion;
	public final FinalTechnique technique;
	public final Widget widget;

	public AlchemistOrder(Potion potion, FinalTechnique technique, Widget widget) {
		this.potion = potion;
		this.technique = technique;
		this.widget = widget;
	}

	public String toString() {
		return "AlchemistOrder[potion=" + potion + ",technique=" + technique + "]";
	}
}
