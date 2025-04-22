package com.couchbase.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.spy.memcached.compat.CloseUtil;

public class CouchbaseProperties {

  private static Properties fileProperties = null;

  private static final Logger LOGGER = Logger.getLogger(
    CouchbaseProperties.class.getName());

  private static String namespace = "cbclient";

  public static void setPropertyFile(String filename) {
      FileInputStream fs = null;
      try {
        URL url =  ClassLoader.getSystemResource(filename);
        if (url != null) {
          fs = new FileInputStream(new File(url.getFile()));
          fileProperties.load(fs);
        }
        LOGGER.log(Level.INFO, "Successfully loaded properties file \"{0}\".",
          filename);
      } catch (IOException e) {
        LOGGER.log(Level.WARNING, "Could not load properties file \"{0}\" "
          + "because of: {1}", new Object[]{filename, e.getMessage()});
      } finally {
        if (fs != null) {
          CloseUtil.close(fs);
        }
      }
  }

  public static String getProperty(String name, String def, boolean ignore) {
    if(ignore == false) {
      name = namespace + "." + name;
    }

    String systemProperty = System.getProperty(name, null);
    if(systemProperty != null) {
      return systemProperty;
    }

    if(fileProperties != null
      && fileProperties.getProperty(name, null) != null) {
      return fileProperties.getProperty(name, null);
    }

    return def;
  }

  public static String getProperty(String name, boolean ignore) {
    return getProperty(name, null, ignore);
  }

  public static String getProperty(String name) {
    return getProperty(name, null, false);
  }

  public static String getProperty(String name, String def) {
    return getProperty(name, def, false);
  }

  public static String getNamespace() {
    return namespace;
  }

  public static void setNamespace(String ns) {
    namespace = ns;
  }

}
