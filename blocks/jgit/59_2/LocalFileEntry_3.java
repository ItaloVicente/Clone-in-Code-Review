          @Override
          public void close()
                  throws IOException {
            super.close();
            unlock();
          }
        };
      }
      else {
        fileOutputStream = new GitLockOutputStream(getLocalFile()
                overwrite);
      }

    }
    else {
      outstreamFile = getLocalFile();
      if (!isExists()) {
        fileOutputStream = new FileOutputStream(outstreamFile);
