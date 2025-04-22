    if (null == bucketName || bucketName != reSubBucket) {
      throw new IllegalArgumentException("Bucket name cannot be null and must"
        + " never be re-set to a new object.");
    }
    if (null == rec || rec != reSubRec) {
      throw new IllegalArgumentException("Reconfigurable cannot be null and"
        + " must never be re-set to a new object");
    }
    reSubBucket = bucketName;  // More than one subscriber, would be an error
    reSubRec = rec;
