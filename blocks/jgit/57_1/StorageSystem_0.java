
package org.eclipse.jgit.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public interface Entry {

  public String getName();

  public String getAbsolutePath();

  public boolean isDirectory();

  public boolean isExists();

  public boolean mkdirs();

  public URI getURI();

  public long length();

  public InputStream getInputStream()
          throws IOException;

  public OutputStream getOutputStream(boolean overwrite)
          throws IOException;

  public Entry[] getChildren();

  public Entry getChild(String name);

  public Entry getParent();

  public StorageSystem getStorageSystem();
}
