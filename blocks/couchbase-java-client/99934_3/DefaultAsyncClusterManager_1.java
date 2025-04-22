                            CompressionMode compressionMode = null;
                            String rawCompressionMode = bucket.getString("compressionMode");
                            if (rawCompressionMode != null && !rawCompressionMode.isEmpty()) {
                                if ("off".equalsIgnoreCase(rawCompressionMode)) {
                                    compressionMode = CompressionMode.OFF;
                                } else if ("active".equalsIgnoreCase(rawCompressionMode)) {
                                    compressionMode = CompressionMode.ACTIVE;
                                } else {
                                    compressionMode = CompressionMode.PASSIVE;
                                }
                            }

