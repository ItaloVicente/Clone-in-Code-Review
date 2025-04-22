  public JsonDocument newDocument(final String id, final JsonObject content, final long cas, final int expiry, final ResponseStatus status) {
    return JsonDocument.create(id, content, cas, expiry, status);
  }

  @Override
  public JsonObject decode(final CachedData cachedData) {
    return decode(cachedData.getBuffer(), cachedData.getFlags());
  }

  @Override
  public JsonObject decode(final ByteBuf buffer, final int flags) {
