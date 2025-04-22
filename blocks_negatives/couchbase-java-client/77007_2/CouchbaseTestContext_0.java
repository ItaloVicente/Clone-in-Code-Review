            boolean existing = clusterManager.hasBucket(bucketName);
            if (!existing) {
                if (createIfMissing) {
                    clusterManager.insertBucket(bucketSettingsBuilder.build());
                } else {
                    throw new BucketDoesNotExistException("Bucket " + bucketName + " doesn't exist and bucket creation disabled (or a sample)");
