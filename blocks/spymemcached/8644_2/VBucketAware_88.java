  void setVBucket(String key, short vbucket);

  short getVBucket(String key);

  Collection<MemcachedNode> getNotMyVbucketNodes();

  void addNotMyVbucketNode(MemcachedNode node);

  void setNotMyVbucketNodes(Collection<MemcachedNode> nodes);
