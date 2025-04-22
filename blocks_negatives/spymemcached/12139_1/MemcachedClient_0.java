  private void validateKey(String key) {
    byte[] keyBytes = KeyUtil.getKeyBytes(key);
    if (keyBytes.length > MAX_KEY_LENGTH) {
      throw new IllegalArgumentException("Key is too long (maxlen = "
          + MAX_KEY_LENGTH + ")");
    }
    if (keyBytes.length == 0) {
      throw new IllegalArgumentException(
          "Key must contain at least one character.");
    }
    for (byte b : keyBytes) {
      if (b == ' ' || b == '\n' || b == '\r' || b == 0) {
        throw new IllegalArgumentException(
            "Key contains invalid characters:  ``" + key + "''");
      }
    }
  }

