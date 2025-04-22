      if (locator instanceof VBucketNodeLocator) {
        VBucketNodeLocator vbucketLocator = (VBucketNodeLocator) locator;
        short vbucketIndex = (short) vbucketLocator.getVBucketIndex(key);
        if (o instanceof VBucketAware) {
          VBucketAware vbucketAwareOp = (VBucketAware) o;
          vbucketAwareOp.setVBucket(key, vbucketIndex);
          if (!vbucketAwareOp.getNotMyVbucketNodes().isEmpty()) {
            MemcachedNode alternative =
                vbucketLocator.getAlternative(key,
                    vbucketAwareOp.getNotMyVbucketNodes());
            if (alternative != null) {
              placeIn = alternative;
            }
          }
        }
      }
