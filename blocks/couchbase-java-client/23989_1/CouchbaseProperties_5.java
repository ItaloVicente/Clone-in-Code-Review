    FileInputStream fs = null;
    try {
      if(filename == null) {
        throw new IllegalArgumentException(
          "Given property filename is null.");
      }

      URL url =  ClassLoader.getSystemResource(filename);
      if (url != null) {
        String clFilename = url.getFile();
        File propFile = new File(clFilename);
        fs = new FileInputStream(propFile);
        fileProperties.load(fs);
      } else {
        throw new IOException("File not found with system classloader.");
