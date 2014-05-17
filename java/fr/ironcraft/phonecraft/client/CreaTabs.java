package fr.ironcraft.phonecraft.client;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

/**
 * @author Dermenslof, DrenBx
 */
public class CreaTabs extends CreativeTabs
{
	public static final CreativeTabs phoneTab = new CreaTabs(CreativeTabs.getNextID(), "PhoneTab", Item.getItemFromBlock(Blocks.bedrock));

	private Item itemIcon;

	public CreaTabs(int par1, String par2Str, Item item)
	{
		super(par1, par2Str);
		this.itemIcon = item;
	}

	@Override
	public Item getTabIconItem()
	{
		return this.itemIcon;
	}
}
