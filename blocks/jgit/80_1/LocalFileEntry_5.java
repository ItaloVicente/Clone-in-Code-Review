
  public boolean create()
          throws IOException {
    return getLocalFile().createNewFile();
  }

  public boolean delete()
          throws IOException {
    return getLocalFile().delete();
  }

  @Override
  protected void performLock()
          throws Exception {
    boolean lock = performTryLock(Long.MAX_VALUE
    if (!lock) {
      throw new IOException("Could not attain lock!");
    }
  }

  @Override
  protected boolean performTryLock(long time
                                   TimeUnit unit) {
    final long milliSecTime = TimeUnit.MILLISECONDS.convert(time
    long waitLeft = milliSecTime;
    boolean tryAgain = true;
    do {
      long nextWaitDuration = Math.min(waitLeft
      waitLeft = waitLeft - nextWaitDuration;
      boolean success = performTryLock();
      if (success) {
        return success;
      }
      else {
        if (nextWaitDuration > 0) {
          try {
            Thread.sleep(nextWaitDuration);
          }
          catch (InterruptedException ex) {
          }
        }
        else {
          tryAgain = false;
        }
      }
    }
    while (tryAgain);
    return false;
  }

  @Override
  protected boolean performTryLock() {
    return createLockFile() != null;
  }

  @Override
  protected void performUnlock() {
    final File lockFile = getLockFile();
    if (lockFile.exists()) {
      lockFile.delete();
    }
  }

  protected File getLockFile() {
    StringBuilder lockFileNameBuilder = new StringBuilder();
    lockFileNameBuilder.append(getLocalFile().getName());
    lockFileNameBuilder.append(LOCK_FILE_EXT);
    final File parent = getLocalFile().getParentFile();
    if (parent != null) {
      parent.mkdirs();
    }
    File localLockFile = new File(parent
    return localLockFile;
  }

  protected File createLockFile() {
    File localLockFile = getLockFile();
    if (localLockFile.exists()) {
      localLockFile = null;
    }
    else {
      boolean createNewFile;
      try {
        createNewFile = localLockFile.createNewFile();
      }
      catch (IOException ex) {
        createNewFile = false;
      }
      if (!createNewFile) {
        localLockFile = null;
      }
    }
    return localLockFile;
  }
