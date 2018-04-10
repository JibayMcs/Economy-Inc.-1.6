package fr.fifou.economy;

import java.io.File;
import java.sql.SQLException;

import fr.fifou.economy.mysql.MySQL;
import net.minecraftforge.common.config.Configuration;

public class EconomyServer extends EconomyCommon 
{
	@Override
    public void preInit(File configFile)
    {
        super.preInit(configFile);  
    }

    @Override
    public void init()
    {
    	if(ConfigFile.connectDB)
        {
        	MySQL.login(ConfigFile.urlDB, ConfigFile.userDB, ConfigFile.passwordDB, ConfigFile.dbName);
        	try
        	{
				MySQL.createDataBase();
			} 
        	catch (SQLException e) 
        	{
				e.printStackTrace();
				System.out.println("Problème de connexion à la base de donnée ou tableau déjà existant.");
			}
        }
        super.init(); 
    }
}
