  @SuppressWarnings("unchecked")
  public <D extends Document<?>> D decode(final String id, final CoreDocument coreDocument, final ResponseStatus status) {
    if (coreDocument == null) {
      return (D) JsonDocument.create(id, null, 0, 0, status);
    }

