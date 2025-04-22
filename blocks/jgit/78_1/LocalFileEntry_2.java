
package org.eclipse.jgit.io;

import java.io.IOException;
import java.net.URI;
import java.util.Hashtable;
import java.util.Map;
import org.eclipse.jgit.io.localfs.LocalFileSystem;

public class StorageSystemManager {

  private static Map<String

  static {
    storageSystemMap = new Hashtable<String
    try {
      register(LocalFileSystem.class);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static StorageSystem getStorageSystem(String scheme)
          throws IOException {
    if (scheme != null && storageSystemMap.containsKey(scheme)) {
      return storageSystemMap.get(scheme);
    }
    else {
      throw new IOException("Scheme ( " + scheme +
                            " ) not registered with manager!");
    }
  }

  public static Entry getEntry(URI uri)
          throws IOException
                 NullPointerException {
    String scheme = uri.getScheme();
    return getStorageSystem(scheme).getEntry(uri);
  }

  public static void register(String storageSystemClassName)
          throws ClassNotFoundException
                 InstantiationException
                 IllegalAccessException
                 ClassCastException {
    register((Class<? extends StorageSystem>) Class.forName(
            storageSystemClassName));
  }

  public static void register(
          Class<? extends StorageSystem> storageSystemClass)
          throws InstantiationException
                 IllegalAccessException {
    if (storageSystemClass != null) {
      StorageSystem system = storageSystemClass.newInstance();
      register(system);
    }
  }

  public static void register(StorageSystem system) {
    if (system != null) {
      storageSystemMap.put(system.getURIScheme()
    }
  }
}
