
  public void testUpdateBucketPswd() throws Exception {
    manager.createNamedBucket(BucketType.COUCHBASE,BUCKET, 100, 0, "", true);
    Thread.sleep(1000);
    manager.updateBucket(BUCKET, 100, AuthType.SASL, 0, 11212, "password", true);
  }

  public void testUpdateBucketRAM() throws Exception {
    manager.createNamedBucket(BucketType.COUCHBASE,BUCKET, 100, 0, "", true);
    Thread.sleep(1000);
    manager.updateBucket(BUCKET, 200, AuthType.SASL, 0, 11212, "", true);
  }

  public void testUpdateBucketAuth() throws Exception {
    manager.createNamedBucket(BucketType.COUCHBASE,BUCKET, 100, 0, "", true);
    Thread.sleep(1000);
    manager.updateBucket(BUCKET, 100, AuthType.NONE, 0, 11212, "", true);
  }

  public void testUpdateBucketPort() throws Exception {
    manager.createPortBucket(BucketType.COUCHBASE,BUCKET, 100, 0, 8090, true);
    Thread.sleep(1000);
    manager.updateBucket(BUCKET, 100, AuthType.SASL, 0, 11212, "", true);
  }

  public void testUpdateBucket() throws Exception {
    BucketTool bucketTool = new BucketTool();
    bucketTool.createDefaultBucket(BucketType.COUCHBASE, 256, 0, true);
    Thread.sleep(1000);
    bucketTool.updateBucket("default", AuthType.SASL, 456, 1, 11212, "", true);
  }

}
