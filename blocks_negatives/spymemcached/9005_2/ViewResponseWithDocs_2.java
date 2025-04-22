    rows = r;
    errors = e;
    for (ViewRow row : rows) {
      map.put(row.getId(), row.getDocument());
    }
  }

  public void addError(RowError r) {
    errors.add(r);
  }

  public Collection<RowError> getErrors() {
    return errors;
  }

  @Override
  public Iterator<ViewRow> iterator() {
    return rows.iterator();
