   * BinaryConnectionFactory. Also the ConnectionFactory's protocol and locator
   * values are always overwritten. The protocol will always be binary and the
   * locator will be chosen based on the bucket type you are connecting to.
   *
   * To connect to the "default" special bucket for a given cluster, use an
   * empty string as the password.
   *
   * If a password has not been assigned to the bucket, it is typically an empty
   * string.
