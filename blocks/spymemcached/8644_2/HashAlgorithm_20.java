  static {
    try {
      MD5_DIGEST = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("MD5 not supported", e);
    }
  }
