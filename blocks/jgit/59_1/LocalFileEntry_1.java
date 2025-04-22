          fileOutputStream = new FileOutputStream(getLocalFile()
        }
      }
      if (lock) {
        final FileLock fileLock;
        try {
          fileLock = fileOutputStream.getChannel().lock();
        }
        catch (IOException ex) {
          fileOutputStream.close();
          throw ex;
        }
        catch (Exception ex) {
          fileOutputStream.close();
          throw new IOException(ex);
        }
        if (fileLock == null) {
          fileOutputStream.close();
          throw new OverlappingFileLockException();
