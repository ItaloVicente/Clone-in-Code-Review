  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{CouchbaseConfigConnection to");
    for (MemcachedNode qa : locator.getAll()) {
      sb.append(" ").append(qa.getSocketAddress());
    }
    sb.append("}");
    return sb.toString();
  }

