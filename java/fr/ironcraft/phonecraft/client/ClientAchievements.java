package fr.ironcraft.phonecraft.client;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public class ClientAchievements {

	public Achievement openPhone = new Achievement("openPhonecraft", "TEST", 0, -1, new ItemStack(Blocks.bedrock), null).registerStat().initIndependentStat();
	
}