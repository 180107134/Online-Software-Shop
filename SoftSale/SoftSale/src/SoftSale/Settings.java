package SoftSale;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Settings
{
	private volatile static Settings _instance;
	private boolean _isValid = false;
	private String _studentsDatabase;
	
	public static synchronized Settings getInstance()
	{
		if (_instance == null)
		{
			_instance = new Settings();
		}
		
		return _instance;
	}
	
	private Settings()
	{
		try 
		{	
			InputStream stream = getClass().getResourceAsStream("settings/settings.ini");
			
			Properties prop = new Properties();
			prop.load(stream);
			
			_studentsDatabase = prop.getProperty("OracleDB");
			
			if (_studentsDatabase.isEmpty())
			{
				JOptionPane.showMessageDialog(null,"Can not load database properties");
				return;
			}		
		
		} 
		catch(Exception e)
		{
			System.out.println(e.toString());
			return;
		}
		
		_isValid = true;
	}

	public boolean isValid()
	{
		return _isValid;
	}
	

	public final String getStoreDatabaseName()
	{
		return _studentsDatabase;
	}

}



