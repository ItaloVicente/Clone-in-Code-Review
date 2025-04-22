  private final Collection<ViewRow> rows;
  private final Collection<RowError> errors;

  public ViewResponseNoDocs(final Collection<ViewRow> r,
      final Collection<RowError> e) {
    rows = r;
    errors = e;
  }

  public Collection<RowError> getErrors() {
    return errors;
  }

  public int size() {
    return rows.size();
  }

  @Override
  public Iterator<ViewRow> iterator() {
    return rows.iterator();
