  public Document<T> content(T content) {
    this.content = content;
    return this;
  }

  @Override
  public Document<T> id(String id) {
    this.id = id;
    return this;
  }

  @Override
  public Document<T> cas(long cas) {
    this.cas = cas;
    return this;
