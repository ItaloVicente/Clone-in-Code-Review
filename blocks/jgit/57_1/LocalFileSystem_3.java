
package org.eclipse.jgit.io.localfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import org.eclipse.jgit.io.Entry;
import org.eclipse.jgit.io.StorageSystem;
import org.eclipse.jgit.io.StorageSystemManager;

public class LocalFileEntry
        implements Entry {

  private File localFile;
  private StorageSystem storageSystem;

  protected LocalFileEntry(File localFile
                           StorageSystem storageSystem)
          throws IllegalArgumentException {
    setLocalFile(localFile);
    setStorageSystem(storageSystem);
  }

  protected void setStorageSystem(StorageSystem storageSystem)
          throws IllegalArgumentException {
    if (storageSystem == null) {
      throw new IllegalArgumentException("Storage system can't be NULL!");
    }
    this.storageSystem = storageSystem;
  }

  public File getLocalFile() {
    return localFile;
  }

  protected void setLocalFile(File localFile)
          throws IllegalArgumentException {
    if (localFile == null) {
      throw new IllegalArgumentException(
              "Local file to be set can't be NULL");
    }
    this.localFile = localFile;
  }

  public String getName() {
    return getLocalFile().getName();
  }

  public String getAbsolutePath() {
    return getLocalFile().getAbsolutePath();
  }

  public boolean isDirectory() {
    return getLocalFile().isDirectory();
  }

  public boolean isExists() {
    return getLocalFile().exists();
  }

  public boolean mkdirs() {
    return getLocalFile().mkdirs();
  }

  public URI getURI() {
    return getLocalFile().toURI();
  }

  public InputStream getInputStream()
          throws IOException {
    if (getLocalFile().exists()) {
      try {
        return new FileInputStream(getLocalFile());
      }
      catch (FileNotFoundException ex) {
        throw ex;
      }
    }
    else {
      throw new FileNotFoundException("File does not exists!");
    }
  }

  public OutputStream getOutputStream(boolean overwrite)
          throws IOException {
    try {
      if (!isExists()) {

        return new FileOutputStream(getLocalFile());
      }
      else {
        if (overwrite) {
          return new FileOutputStream(getLocalFile());
        }
        else {
          return new FileOutputStream(getLocalFile()
        }
      }
    }
    catch (FileNotFoundException ex) {
      throw ex;
    }
  }

  public Entry[] getChildren() {
    File[] children = getLocalFile().listFiles();
    if (children == null || children.length == 0) {
      return new Entry[0];
    }
    else {
      Entry[] entries = new Entry[children.length];
      for (int i = 0; i < children.length; ++i) {
        entries[i] = getStorageSystem().getEntry(children[i].toURI());
      }
      return entries;
    }
  }

  public Entry getChild(String name) {
    if (name == null || name.length() == 0) {
      return null;
    }
    Entry[] children = getChildren();
    for (Entry entry : children) {
      if (name.equals(entry.getName())) {
        return entry;
      }
    }
    return null;
  }

  public Entry getParent() {
    File parent = getLocalFile().getParentFile();
    if (parent == null) {
      return null;
    }
    else {
      return getStorageSystem().getEntry(parent.toURI());
    }
  }

  public StorageSystem getStorageSystem() {
    return storageSystem;
  }

  public long length() {
    return getLocalFile().length();
  }
}
