package fr.epsi.bo;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import fr.epsi.location.remote.ILocation;

public class ServiceJNDI {

	public static final String DEFAULT_JNDI_NAME = "LocationBean/remote";
	
	public static ILocation getBeanFromContext() {
		
		try{
			System.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		    System.setProperty(" java.naming.factory.url.pkgs"," org.jboss.naming.org.jnp.interfaces");
		    System.setProperty("java.naming.provider.url", "localhost:1099");
		    
			Context context;
			context = new InitialContext();
			Object obj= context.lookup(DEFAULT_JNDI_NAME);
			return (ILocation)PortableRemoteObject.narrow(obj, ILocation.class);
		}
		catch(NamingException ne){
			ne.printStackTrace();
		}
		
		return null;
	}
}
