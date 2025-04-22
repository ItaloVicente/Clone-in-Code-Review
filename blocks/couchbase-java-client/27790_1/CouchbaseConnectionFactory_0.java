
    if (bucketName == null || bucketName.isEmpty()) {
      throw new IllegalArgumentException("The bucket name must not be null "
        + "or empty.");
    }
    if (password == null) {
      throw new IllegalArgumentException("The bucket password must not be "
        + " null.");
