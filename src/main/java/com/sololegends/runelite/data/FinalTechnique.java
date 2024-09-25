package com.sololegends.runelite.data;

public enum FinalTechnique {
	CONCENTRATE(5672),
	HOMOGENISE(5674),
	CRYSTALISE(5673);

	final int sprite_id;

	FinalTechnique(int sprite_id) {
		this.sprite_id = sprite_id;
	}

	public static FinalTechnique fromSpriteID(int sprite_id) {
		if (FinalTechnique.CONCENTRATE.sprite_id == sprite_id) {
			return FinalTechnique.CONCENTRATE;
		} else if (FinalTechnique.HOMOGENISE.sprite_id == sprite_id) {
			return FinalTechnique.HOMOGENISE;
		} else if (FinalTechnique.CRYSTALISE.sprite_id == sprite_id) {
			return FinalTechnique.CRYSTALISE;
		}
		return null;
	}
}
