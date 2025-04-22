        while (indexedBucket == null) {
            try {
                indexedBucket = cluster.openBucket(indexedBucketName);
            } catch (Exception e) {
                LOGGER.info("Unable to open bucket" + e.getMessage());
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
