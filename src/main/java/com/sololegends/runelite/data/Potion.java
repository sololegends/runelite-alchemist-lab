package com.sololegends.runelite.data;

import java.util.ArrayList;

public enum Potion {
	MAMMOTH_MIGHT_MIX("Mammoth-might mix", 30011, 1, 1, 1),
	MYSTIC_MANA_AMALGAM("Mystic mana amalgam", 30012, 1, 1, 2),
	MARLEYS_MOONLIGHT("Marley's moonlight", 30013, 1, 1, 3),
	MIXALOT("Mixalot", 30020, 1, 2, 3),
	ALCO_AUGMENTATOR("Alco-augmentator", 30014, 2, 2, 2),
	AZURE_AURA_MIX("Azure aura mix", 30016, 2, 2, 1),
	AQUALUXE_AMALGAM("Aqualux amalgam", 30015, 2, 2, 3),
	LIPLACK_LIQUOR("Liplack liquor", 30017, 3, 3, 3),
	MEGALITE_LIQUID("Megalite liquid", 30019, 3, 3, 1),
	ANTI_LEECH_LOTION("Anti-leech lotion", 30018, 3, 3, 2);

	public final int i1, i2, i3;
	public final String name;
	public final int id;

	Potion(String name, int i1, int i2, int i3) {
		this.name = name;
		this.i1 = i1;
		this.i2 = i2;
		this.i3 = i3;
		this.id = -1;
	}

	Potion(String name, int id, int i1, int i2, int i3) {
		this.name = name;
		this.i1 = i1;
		this.i2 = i2;
		this.i3 = i3;
		this.id = id;
	}

	public boolean matches(int i1, int i2, int i3) {
		if (this.i1 == i1 && this.i2 == i2 && this.i3 == i3) {
			return true;
		} else if (this.i1 == i1 && this.i2 == i3 && this.i3 == i2) {
			return true;
		} else if (this.i1 == i2 && this.i2 == i1 && this.i3 == i3) {
			return true;
		} else if (this.i1 == i2 && this.i2 == i3 && this.i3 == i1) {
			return true;
		} else if (this.i1 == i3 && this.i2 == i2 && this.i3 == i1) {
			return true;
		} else if (this.i1 == i3 && this.i2 == i1 && this.i3 == i2) {
			return true;
		}
		return false;
	}

	public PotionIngredient[] needs(int h_i1, int h_i2, int h_i3) {
		ArrayList<PotionIngredient> in_hopper = new ArrayList<>();
		if (h_i1 > 0 && h_i1 < 4) {
			in_hopper.add(PotionIngredient.values()[h_i1]);
		}
		if (h_i2 > 0 && h_i2 < 4) {
			in_hopper.add(PotionIngredient.values()[h_i2]);
		}
		if (h_i3 > 0 && h_i3 < 4) {
			in_hopper.add(PotionIngredient.values()[h_i3]);
		}
		ArrayList<PotionIngredient> have = new ArrayList<>() {
			{
				add(PotionIngredient.values()[i1]);
				add(PotionIngredient.values()[i2]);
				add(PotionIngredient.values()[i3]);
			}
		};
		ArrayList<PotionIngredient> needed = new ArrayList<>();
		for (PotionIngredient i : have) {
			if (in_hopper.contains(i)) {
				in_hopper.remove(i);
				continue;
			}
			needed.add(i);
		}
		return needed.toArray(new PotionIngredient[] {});
	}

	public static Potion fromName(String name) {
		for (Potion pot : Potion.values()) {
			if (pot.name.equals(name)) {
				return pot;
			}
		}
		return null;
	}

	public static Potion getPotion(int id) {
		for (Potion pot : Potion.values()) {
			if (pot.id == id) {
				return pot;
			}
		}
		return null;
	}

	public static Potion getFinishedPotion(int id) {
		for (Potion pot : Potion.values()) {
			if (pot.id + 10 == id) {
				return pot;
			}
		}
		return null;
	}

}
