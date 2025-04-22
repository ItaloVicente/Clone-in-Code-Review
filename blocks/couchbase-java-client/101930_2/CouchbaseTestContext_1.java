                        boolean bucketExists = false;
                        while (!bucketExists) {
                            try {
                                cluster.openBucket(bucketName);
                                bucketExists = true;
                            } catch (Exception e) {
                            }
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                throw new RuntimeException(e);
                            }
                        }
