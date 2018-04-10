package fr.fifou.economy.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.fifou.economy.capability.CapabilityLoading;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class MySQL {
    
    private static Statement statement = null;
    private static Connection conn = null;
    private static ResultSet resultSet = null;

    private static boolean loggedIn = false;
    
    public static void login(String url, String user, String pass,String dbName) //Connection to the database.
    {
        try 
        {
        	Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+ url + "/" + dbName , user, pass);
    		System.out.println("Connexion à l'hôte réussi.");		
            loggedIn = true;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        
    }
    
    public static void createDataBase() throws SQLException //Creation of the data base if it doesn't exist.
    {
        Statement  state = null;
    	if(loggedIn)
    	{
    		try
    		{
	    		state = conn.createStatement();
	    		String query = "CREATE TABLE EconomyInc(id INT(11) UNIQUE AUTO_INCREMENT," + "name VARCHAR(255)," + "uuid VARCHAR(255)," + "money DOUBLE," + "linked BOOLEAN," + "status VARCHAR(255)," + "synchroBDDtoSERV BOOLEAN)";
	    		state.executeUpdate(query);
	    		System.out.println("Base de donnée créée avec succès.");

    		}
    		catch(SQLException e)
    		{
    			e.printStackTrace();
	    		System.out.println("Base de donnée déjà existante ou autres problèmes(voir logs serveurs).");
    		}
    		finally
            {
                try
                {
                    if(state != null && !state.isClosed())
                    state.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
    	}
    	else
    	{
    		System.out.println("Merci de vous connectez à la base de donnée en premier.");
    	}
    }
      
    public static void check(EntityPlayer player) //Checking lot of stuff in here.
    {
    	PreparedStatement state = null; //state for the select
    	PreparedStatement stateUP = null; //state for the update
    	String userUUID = ""; //creating a user uuid string for non null
    	boolean synchronizeBDDToServer = false;  //creating a boolean synchronizeBDDToServer for always be sure it's false by default
    	double money = 0; //creating a money field double for non null and be sure it's 0
     	if(loggedIn)//Always check if we are connected to the DB
     	{
     		try
     		{
     			String selectSQL = "SELECT * FROM economyinc WHERE uuid = ?";
     			state = conn.prepareStatement(selectSQL);
     			state.setString(1, player.getUniqueID().toString());
     			ResultSet rs = state.executeQuery();
     			
     			while(rs.next())
     			{
     				userUUID = rs.getString("uuid");
     				synchronizeBDDToServer = rs.getBoolean("synchroBDDtoSERV");
     				money = rs.getDouble("money");
     			}

     				if(!userUUID.equals(player.getUniqueID().toString()))//If there is not players with the same uuid we insert a new one in the database with the uuid.
     				{
     					MySQL.insert(player); 
     				}
     				else
     				{
     					
     					MySQL.putOnline(player);
     					
     					if(synchronizeBDDToServer)//If the value of the boolean(tinyint 0/1) is set to 1 it will synchronize the db → server.
     					{
     						player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).setMoney(money);//We set the capability of the player to the amount found in the db
     						try
         		     		{
         		     			String updateSQL = "UPDATE economyinc SET synchroBDDtoSERV = ? WHERE uuid = ?";
         		     			stateUP = conn.prepareStatement(updateSQL);
         		     			stateUP.setBoolean(1, false);
         		     			stateUP.setString(2, player.getUniqueID().toString());
         		     			stateUP.executeUpdate();
         		     		}
         		     		catch(SQLException e)
         		    		{
         		    			e.printStackTrace();
         		    		}
         		    		finally
         		            {
         		                try
         		                {
         		                    if(stateUP != null && !stateUP.isClosed())
         		                    stateUP.close();
         		                }
         		                catch(SQLException e)
         		                {
         		                    e.printStackTrace();
         		                }
         		            }
     					}
     		     	}
     		}
     		catch(SQLException e)
    		{
    			e.printStackTrace();
    		}
    		finally
            {
                try
                {
                    if(state != null && !state.isClosed())
                    state.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
     	}
     		
    }
      
    public static void insert(EntityPlayer player)//FORM to insert a new player in the database
    {
    	PreparedStatement state = null;
     	if(loggedIn)
     	{
     		try
     		{
     			String selectSQL = "INSERT INTO economyinc(name,uuid,money,linked,status,synchroBDDtoSERV) VALUES (?,?,?,?,?,?)";
     			state = conn.prepareStatement(selectSQL);
     			state.setString(1, player.getName());
     			state.setString(2, player.getUniqueID().toString());
     			state.setDouble(3, player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney());
     			state.setBoolean(4, player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getLinked());
     			state.setString(5, "online");
     			state.setBoolean(6, false);
     			state.executeUpdate();
     			
     		}
     		catch(SQLException e)
    		{
    			e.printStackTrace();
    		}
    		finally
            {
                try
                {
                    if(state != null && !state.isClosed())
                    state.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
     	}
    }
    
    public static void update(EntityPlayer player)//Updating the player money, linked, status when he log out.
    {
    	PreparedStatement state = null;
     	if(loggedIn)
     	{
     		try
     		{
     			String updateSQL = "UPDATE economyinc SET money = ?, linked = ?, status = ? WHERE uuid = ?";
     			state = conn.prepareStatement(updateSQL);
     			state.setDouble(1, player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getMoney());
     			state.setBoolean(2, player.getCapability(CapabilityLoading.CAPABILITY_MONEY, null).getLinked());
     			state.setString(3, "offline");
     			state.setString(4, player.getUniqueID().toString());
     			state.executeUpdate();
     		}
     		catch(SQLException e)
    		{
    			e.printStackTrace();
    		}
    		finally
            {
                try
                {
                    if(state != null && !state.isClosed())
                    state.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
     	}
    }
       
    public static void putOnline(EntityPlayer player)//Putting the player online.
    {
    	PreparedStatement stateUP = null; //state for the update

    	try//If there is already one, we update him to put him online in the database.
  		{
  			String updateSQL = "UPDATE economyinc SET status = ? WHERE uuid = ?";
  			stateUP = conn.prepareStatement(updateSQL);
  			stateUP.setString(1, "online");
  			stateUP.setString(2, player.getUniqueID().toString());
  			stateUP.executeUpdate();
  		}
  		catch(SQLException e)
 		{
 			e.printStackTrace();
 		}
 		finally
         {
             try
             {
                 if(stateUP != null && !stateUP.isClosed())
                 stateUP.close();
             }
             catch(SQLException e)
             {
                 e.printStackTrace();
             }
         }
    }
}



