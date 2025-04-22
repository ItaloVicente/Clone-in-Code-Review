                            CompressionMode compressionMode = null;
                            String rawCompressioMode = bucket.getString("compressionMode");
                            if (rawCompressioMode != null && !rawCompressioMode.isEmpty()) {
                                if ("off".equalsIgnoreCase(rawCompressioMode)) {
                                    compressionMode = CompressionMode.OFF;
                                } else if ("active".equalsIgnoreCase(rawCompressioMode)) {
                                    compressionMode = CompressionMode.ACTIVE;
                                } else {
                                    compressionMode = CompressionMode.PASSIVE;
                                }
                            }

