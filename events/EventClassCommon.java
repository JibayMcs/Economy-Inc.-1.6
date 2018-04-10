package fr.fifou.economy.events;

import java.util.Random;

import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventClassCommon {

	private static final Random rand = new Random();
	
    @SubscribeEvent
    public static void modifyNBT(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack s = event.getObject();
        if(s.hasTagCompound())
            return;
        if(s.getItem() == ItemsRegistery.ITEM_GOLDNUGGET) {   	
        	double valeur = rand.nextDouble();
        	String valeur2 = Double.toString(valeur);
        	String valeur3 = valeur2.substring(0,4);
            s.setTagInfo("weight", new NBTTagString(valeur3));
        }
    }
    
}
