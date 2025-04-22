          Collection<MemcachedNode> notMyVbucketNodes =
            vbucketAwareOp.getNotMyVbucketNodes();
          if (!notMyVbucketNodes.isEmpty()) {
            cf.checkConfigUpdate();
            MemcachedNode alternative = vbucketLocator.getAlternative(key,
              notMyVbucketNodes);
            if (alternative == null || !alternative.isActive()) {
              notMyVbucketNodes.clear();
            } else {
