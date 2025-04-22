                    try {
                        String config = response.content().toString(CharsetUtil.UTF_8).trim();
                        if (config.startsWith("{")) {
                            configurationProvider.proposeBucketConfig(response.bucket(), config);
                        }
                    } finally {
                        response.content().release();
                    }
