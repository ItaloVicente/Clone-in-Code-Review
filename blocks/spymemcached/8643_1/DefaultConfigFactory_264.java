    StringBuilder sb = new StringBuilder();
    try {
      FileInputStream fis = new FileInputStream(filename);
      BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
      String str;
      while ((str = reader.readLine()) != null) {
        sb.append(str);
      }
    } catch (IOException e) {
      throw new ConfigParsingException("Exception reading input file: "
          + filename, e);
