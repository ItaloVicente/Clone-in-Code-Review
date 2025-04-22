  public ViewQueryMapper(Map<Class<?>, Converter<?, ?>> converters) {
    this.converters = converters;
  }

  @Override
  public Observable<JsonObject> call(final ViewQueryResponse response) {
    final Converter<?, ?> converter = converters.get(JsonDocument.class);
    final MarkersProcessor processor = new MarkersProcessor();
    response.content().forEachByte(processor);

    final List<Integer> markers = processor.markers();
    final List<JsonObject> objects = new ArrayList<JsonObject>();
    for (final Integer marker : markers) {
      final ByteBuf chunk = response.content().readBytes(marker - response.content().readerIndex());
      chunk.readerIndex(chunk.readerIndex() + 1);
      objects.add(((JacksonJsonConverter) converter).decode(chunk.toString(CharsetUtil.UTF_8)));
