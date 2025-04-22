    final OutputStream fileOutputStream;
    final File outstreamFile;
    if (lock) {
      boolean newLock = !isHeldByCurrentThread();
      if (!tryLock()) {
        throw new IOException("Could not attain lock!");
      }
      outstreamFile = getLockFile();
      if (newLock) {
        fileOutputStream = new GitLockOutputStream(getLocalFile()
                overwrite) {
