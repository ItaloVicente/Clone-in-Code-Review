    }, "Couchbase View Thread");
    reactorThread.start();

    running = true;
  }

  private void updateMaxTotalRequests() {
    int size = viewNodes.size();

    if (size > 0) {
      pool.setMaxTotal(viewNodes.size() * pool.getDefaultMaxPerRoute());
    } else {
      getLogger().warn("No View nodes are present, this could be a bug or " + "no node has vbuckets attached.");
      pool.setMaxTotal(1);
    }
  }
