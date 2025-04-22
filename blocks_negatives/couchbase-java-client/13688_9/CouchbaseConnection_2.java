      if (locator instanceof VBucketNodeLocator) {
        ((VBucketNodeLocator)locator).updateLocator(mergedNodes,
            bucket.getConfig());
      } else {
        locator.updateLocator(mergedNodes);
      }
