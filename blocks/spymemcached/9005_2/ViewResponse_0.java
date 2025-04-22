  @Override
  public Iterator<ViewRow> iterator() {
    return rows.iterator();
  }

  public int size() {
    return rows.size();
  }

  public abstract Map<String, Object> getMap();
