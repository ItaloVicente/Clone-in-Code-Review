    if (hasVBucketCheckpoints) {
      bb.putShort((short)vBucketCheckpoints.size());
      for (Short vBucket : vBucketCheckpoints.keySet()) {
        bb.putShort(vBucket);
        bb.putLong(vBucketCheckpoints.get(vBucket));
      }
    }
