
package org.eclipse.jgit.io.localfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import org.eclipse.jgit.io.utils.IOUtils;

public class GitLockOutputStream
        extends OutputStream {

  private File mainFile;
  private File lockFile;
  private boolean appendMode;
  private FileLock fileLock;
  private FileOutputStream lockFileOutputStream;

  public GitLockOutputStream(File mainFile
                             File lockFile
                             boolean appendMode)
          throws IOException
                 IllegalArgumentException {

    this.mainFile = mainFile;
    this.lockFile = lockFile;
    this.appendMode = appendMode;
    init();
  }

  @Override
  public void close()
          throws IOException {
    if (fileLock != null) {
      fileLock.release();
    }
    lockFileOutputStream.close();
    if (fileLock != null) {
      if (!lockFile.renameTo(mainFile)) {
        if (!mainFile.exists() || mainFile.delete()) {
          if (lockFile.renameTo(mainFile)) {
            throw new IOException(new StringBuilder("Could not rename ").append(lockFile.
                    getAbsolutePath()).append(" to ").append(mainFile.
                    getAbsolutePath()).toString());
          }
        }
      }
    }
  }

  @Override
  public void flush()
          throws IOException {
    lockFileOutputStream.flush();
  }

  @Override
  public void write(int b)
          throws IOException {
    lockFileOutputStream.write(b);
  }

  @Override
  public void write(byte[] b)
          throws IOException {
    lockFileOutputStream.write(b);
  }

  @Override
  public void write(byte[] b
                    int off
                    int len)
          throws IOException {
    lockFileOutputStream.write(b
  }

  protected void init()
          throws IOException {
    if (mainFile.exists() && appendMode) {
      FileInputStream inputStream = new FileInputStream(mainFile);
      FileOutputStream outputStream = new FileOutputStream(lockFile);
      IOUtils.copy(inputStream
      inputStream.close();
      outputStream.close();
    }
    lockFileOutputStream = new FileOutputStream(lockFile
    try {
      fileLock = lockFileOutputStream.getChannel().lock();
    }
    catch (IOException ex) {
      close();
      throw ex;
    }
    catch (Exception ex) {
      close();
      throw new IOException(ex);
    }
    if (fileLock == null) {
      close();
      throw new OverlappingFileLockException();
    }
  }
}
