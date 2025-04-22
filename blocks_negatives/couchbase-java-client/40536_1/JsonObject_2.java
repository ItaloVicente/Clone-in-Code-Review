  public long getLong(String name) {
    Object found = content.get(name);
    if (found == null) {
        throw new NullPointerException();
    }
    if (found instanceof Integer) {
        return (Integer) found;
    }
    return (Long) found;
