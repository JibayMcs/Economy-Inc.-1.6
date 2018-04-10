package fr.fifou.economy.commands;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.items.ItemCreditcard;
import fr.fifou.economy.packets.PacketCardChange;
import fr.fifou.economy.packets.PacketCardChangeAdmin;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.FMLServerHandler;
import net.minecraftforge.server.permission.PermissionAPI;

public class CommandBalance extends CommandBase 
{
	public String getName() 
	{
		return "balance";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) 
	{
	    if (sender instanceof EntityPlayer)
	    	return PermissionAPI.hasPermission((EntityPlayer) sender, "economy.command.balance");
	    return true;
	}

	public String getUsage(ICommandSender arg0) 
	{
		return "commands.balance.usage";
	}

	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(args.length <= 0)
        throw new WrongUsageException("commands.balance.usage", new Object[0]);	
			if (args[0].matches("check"))
			{
		            EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(args[1]);
		            int totalcount = 0;
		            
		            if (player == null)
		            {
		                throw new PlayerNotFoundException("commands.generic.player.notFound", new Object[] {args[1]});
		            }
		            else
		            { 
						notifyCommandListener(sender, this, "Funds of " + player.getName() + " : " + player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney(), new Object[] {player.getName()});
					}
            }
			else if(args[0].matches("add"))
			{
					EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(args[1]);
		            
		            if (player == null)
		            {
		                throw new PlayerNotFoundException("commands.generic.player.notFound", new Object[] {args[1]});
		            }
		            else
		            {

						switch(Integer.valueOf(args[2]))
							{
								case 5: 
								player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() + 5);
								player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
								break;
								case 10:
								player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() + 10);
								player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
								break;
								case 20:
								player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() + 20);
								player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
								break;
								case 50:
								player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() + 50);
								player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
								break;
								case 100:
								player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() + 100);
								player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
								break;
								case 200:
								player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() + 200);
								player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
								break;
								case 500:
								player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() + 500);
								player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
								break;
								default:
								throw new WrongUsageException("commands.balance.funds", new Object[0]);
							}
					}
			}
			else if(args[0].matches("remove"))
			{
				EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(args[1]);
	            if (player == null)
	            {
	                throw new PlayerNotFoundException("commands.generic.player.notFound", new Object[] {args[1]});
	            }
	            else
	            {
	            	switch(Integer.valueOf(args[2]))
					{
						case 5: 
						player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() - 5);
						player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
						break;
						case 10:
						player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() - 10);
						player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
						break;
						case 20:
						player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() - 20);
						player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
						break;
						case 50:
						player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() - 50);
						player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
						break;
						case 100:
						player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() - 100);
						player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
						break;
						case 200:
						player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() - 200);
						player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
						break;
						case 500:
						player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney() - 500);
						player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).sync(player);
						break;
						default:
						throw new WrongUsageException("commands.balance.funds", new Object[0]);
					}
										   
	            }
			}
        	else
        	{
                throw new WrongUsageException("commands.balance.usage", new Object[0]);
        	}

     }
	
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
		switch(args.length)
		{
		case 1:
			return Lists.newArrayList("check","add", "remove");
		case 2:
			 return args.length >= 2 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
		case 3:
			return Lists.newArrayList("5", "10","20","50","100","200","500");
		}
		return Lists.newArrayList();
    }
}

