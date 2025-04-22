  public JsonDocument newDocument(final String id) {
    return JsonDocument.create(id);
  }

  @Override
  public CoreDocument encode(final Document document) {
