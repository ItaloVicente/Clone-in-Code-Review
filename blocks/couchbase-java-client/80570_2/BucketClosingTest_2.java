    Bucket bucket;
    if (ctx.rbacEnabled()) {
      bucket = cluster().openBucket(bucketName());
    } else {
      bucket = cluster().openBucket(bucketName(), password());
    }
