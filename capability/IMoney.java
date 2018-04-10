package fr.fifou.economy.capability;

import fr.fifou.economy.ModEconomy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public interface IMoney {

	double getMoney();
	void setMoney(double money);
	boolean getLinked();
	void setLinked(boolean linked);
	String getName();
	void setName(String name);
	String getOnlineUUID();
	void setOnlineUUID(String onUUID);
	
	void sync(EntityPlayer player);
}
