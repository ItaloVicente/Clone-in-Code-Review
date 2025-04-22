                                    ByteBuf content = res.content();
                                    if (res.status().isSuccess() && content!= null && content.readableBytes() > 0) {
                                        String rawConfig = content
                                            .toString(CharsetUtil.UTF_8)
                                            .replace("$HOST", hostname.getHostName())
                                            .trim();
                                        if (rawConfig.startsWith("{")) {
                                            provider().proposeBucketConfig(res.bucket(), rawConfig);
                                        }
                                    }
                                    if (content != null && content.refCnt() > 0) {
                                        content.release();
                                    }
