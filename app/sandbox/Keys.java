/**
 * 
 */
package sandbox;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author tpearson
 *
 */
public class Keys {

	private static final Config CONFIG = ConfigFactory.load();

	static final public String sandboxKey(){
		return CONFIG.getString("flightsearch.key");
	}

	public static String searchURL() {
		return CONFIG.getString("flightsearch.url");
	}
}
