package fr.fifou.economy.capability;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.packets.PacketMoneyData;
import fr.fifou.economy.packets.PacketsRegistery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class DefaultMoneyHandler implements IMoney {

	private double money;
	private boolean linked = false;
	private String name = "";
	private String onlineUUID = "";
	
	@Override
	public double getMoney() 
	{
		return this.money;
	}

	@Override
	public void setMoney(double money)
	{
		this.money = money;
	}
	
	@Override
	public boolean getLinked() 
	{
		return this.linked;
	}

	@Override
	public void setLinked(boolean linked)
	{
		this.linked = linked;
	}
	
	@Override
	public String getName() 
	{
		return this.name;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}
	
	@Override
	public String getOnlineUUID() 
	{
		return this.onlineUUID;
	}

	@Override
	public void setOnlineUUID(String onUUID)
	{
		this.onlineUUID = onUUID;
	}
	
	@Override
	public void sync(EntityPlayer player) 
	{
		PacketsRegistery.network.sendTo(new PacketMoneyData(this.money, this.linked, this.name, this.onlineUUID), (EntityPlayerMP) player);
	}
	

}
