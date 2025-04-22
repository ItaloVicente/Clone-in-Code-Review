  public static byte[] computeMd5(String k) {
    MessageDigest md5;
    try {
      md5 = (MessageDigest) MD5_DIGEST.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException("clone of MD5 not supported", e);
    }
    md5.update(KeyUtil.getKeyBytes(k));
    return md5.digest();
  }
