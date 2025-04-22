
  public static boolean isJsonObject(String s) {
    if (s.startsWith("{") || s.startsWith("[") || s.equals("true")
        || s.equals("false") || s.equals("null")) {
      return true;
    }
    try {
      new Integer(s);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
