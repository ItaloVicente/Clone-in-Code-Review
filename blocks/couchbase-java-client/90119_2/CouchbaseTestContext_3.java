                for (int i = 0; i < 5; i++) {
                    try {
                        bucketManager.flush();
                        break;
                    } catch (CouchbaseException ex) {
                        if (ex.getMessage() != null && ex.getMessage().contains("Flush failed with unexpected error")) {
                            continue;
                        } else {
                            break;
                        }
                    }
                }
