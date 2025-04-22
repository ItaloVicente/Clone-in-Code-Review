                    VBucketAware vbucketAwareOp = (VBucketAware) o;
                    vbucketAwareOp.setVBucket(vbucketIndex);
                    if (!vbucketAwareOp.getNotMyVbucketNodes().isEmpty()) {
                        MemcachedNode alternative = vbucketLocator.
                                getAlternative(key, vbucketAwareOp.getNotMyVbucketNodes());
                        if (alternative != null) {
                            placeIn = alternative;
                        }
                    }
