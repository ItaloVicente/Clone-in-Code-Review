    request.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
    request.setHeader(HttpHeaders.Names.CACHE_CONTROL,
        HttpHeaders.Values.NO_CACHE);
    request.setHeader(HttpHeaders.Names.ACCEPT, "application/json");
    request.setHeader(HttpHeaders.Names.USER_AGENT,
        "spymemcached vbucket client");
    request.setHeader("X-memcachekv-Store-Client-Specification-Version",
        CLIENT_SPEC_VER);
    return request;
  }

  private void setBucket(Bucket newBucket) {
    if (this.bucket == null || !this.bucket.equals(newBucket)) {
      this.bucket = newBucket;
      setChanged();
      notifyObservers(this.bucket);
