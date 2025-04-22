      FileInputStream fs = null;
      try {
        URL url =  ClassLoader.getSystemResource("cbclient.properties");
        if (url != null) {
          fs = new FileInputStream(new File(url.getFile()));
          properties.load(fs);
        }
        viewmode = properties.getProperty("viewmode");
      } catch (IOException e) {
      } finally {
        if (fs != null) {
          CloseUtil.close(fs);
        }
      }
