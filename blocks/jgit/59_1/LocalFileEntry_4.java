
  public boolean createNew()
          throws IOException {
    return getLocalFile().createNewFile();
  }

  public boolean delete()
          throws IOException {
    return getLocalFile().delete();
  }

  public Entry getLockEntry() {
    if (localLockFile == null) {
      return null;
    }
    else {
      return getStorageSystem().getEntry(localLockFile.toURI());
    }
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
    StringBuilder lockFileNameBuilder = new StringBuilder();
    if (this.lockFileName == null) {
      lockFileNameBuilder.append(getLocalFile().getName());
      lockFileNameBuilder.append(LOCK_FILE_EXT);
      this.lockFileName = lockFileNameBuilder.toString();
    }
    final File parent = getLocalFile().getParentFile();
    if (parent != null) {
      parent.mkdirs();
    }
    localLockFile = new File(parent
    if (localLockFile.exists()) {
      localLockFile = null;
      return false;
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
      return createNewFile;
    }
  }

  @Override
  protected void performUnlock() {
    if (localLockFile != null) {
      localLockFile.delete();
    }
  }
