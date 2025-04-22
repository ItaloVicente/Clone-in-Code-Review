    this.serversWithVBuckets = new HashSet<String>();

    cacheServersWithVBuckets();
  }

  private void cacheServersWithVBuckets() {
    int serverIndex = 0;
    for (String server : servers) {
      for (VBucket vbucket : vbuckets) {
        if (vbucket.getMaster() == serverIndex) {
          serversWithVBuckets.add(server.split(":")[0]);
        }
      }
      serverIndex++;
    }

    getLogger().debug("Nodes with active VBuckets: " + serversWithVBuckets);
