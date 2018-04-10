package fr.fifou.economy.packets;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.mysql.MySQL;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketMoneyData implements IMessage {

	private double money;
	private boolean linked;
	private String name;
	private String onlineUUID;
	
	public PacketMoneyData() {}
	
	public PacketMoneyData(double money, boolean linked, String name, String OnUUID) 
	{
		this.money = money;
		this.linked = linked;
		this.name = name;
		this.onlineUUID = OnUUID;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		this.money = buf.readDouble();
		this.linked = buf.readBoolean();
		this.name = ByteBufUtils.readUTF8String(buf);
		this.onlineUUID = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeDouble(this.money);
		buf.writeBoolean(this.linked);
		ByteBufUtils.writeUTF8String(buf, this.name);
		ByteBufUtils.writeUTF8String(buf, this.onlineUUID);
	}

	public static class PacketMoneyHandlerClient implements IMessageHandler<PacketMoneyData, IMessage>
    {

       @Override
       public IMessage onMessage(final PacketMoneyData message, MessageContext ctx)
       {

          IThreadListener thread = Minecraft.getMinecraft();
          final EntityPlayer player = Minecraft.getMinecraft().player;
          thread.addScheduledTask(() -> {
             if(player != null) {
            	 IMoney capabilities = player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null);
                if(capabilities != null) {
                   capabilities.setMoney(message.money);
                   capabilities.setLinked(message.linked);
                   capabilities.setName(message.name);
                   capabilities.setOnlineUUID(message.onlineUUID);
                }
             }
          });

          return null;
       }
    }

    public static class PacketMoneyHandlerServer implements IMessageHandler<PacketMoneyData, IMessage>
    {
       @Override
       public IMessage onMessage(final PacketMoneyData message, MessageContext ctx)
       {
    	  IThreadListener thread =  ctx.getServerHandler().player.getServerWorld();
          final EntityPlayer player = ctx.getServerHandler().player;
          thread.addScheduledTask(() -> 
          {
             if(player != null) {
                IMoney capabilities = player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null);
                if(capabilities != null) {
                   capabilities.setMoney(message.money);
                   capabilities.setLinked(message.linked);
                   capabilities.setName(message.name);
                   capabilities.setOnlineUUID(message.onlineUUID);
                }
             }
          });
          return null;
       }
    }
}
