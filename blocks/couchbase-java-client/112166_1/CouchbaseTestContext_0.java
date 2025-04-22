                        try {
                            clusterManager.insertBucket(bucketSettingsBuilder.build());
                        } catch(Exception e) {
                            try {
                                Thread.sleep(5000);  // 5 seconds is pretty arbitrary

                            } catch (InterruptedException e2) {
                            }
                            clusterManager.insertBucket(bucketSettingsBuilder.build());
                        }
