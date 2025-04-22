
package org.eclipse.jgit.io;

import java.net.URI;

public interface StorageSystem {

  public String getURIScheme();

  public Entry getEntry(URI uri);

  public Entry getWorkingDirectory();
}
