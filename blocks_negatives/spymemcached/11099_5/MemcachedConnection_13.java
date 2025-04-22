      if (locator instanceof VBucketNodeLocator) {
        if (o instanceof KeyedOperation && o instanceof VBucketAware) {
          Collection<String> keys = ((KeyedOperation) o).getKeys();
          VBucketNodeLocator vbucketLocator = (VBucketNodeLocator) locator;
          for (String key : keys) {
            short vbucketIndex = (short) vbucketLocator.getVBucketIndex(key);
            VBucketAware vbucketAwareOp = (VBucketAware) o;
            vbucketAwareOp.setVBucket(key, vbucketIndex);
          }
        }
      }
