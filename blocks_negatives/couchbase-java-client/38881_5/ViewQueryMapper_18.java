    public Observable<JsonObject> call(ViewQueryResponse response) {
        Converter<?, ?> converter = converters.get(JsonDocument.class);
        MarkersProcessor processor = new MarkersProcessor();
        response.content().forEachByte(processor);
        List<Integer> markers = processor.markers();
        List<JsonObject> objects = new ArrayList<JsonObject>();
        for (Integer marker : markers) {
            ByteBuf chunk = response.content().readBytes(marker - response.content().readerIndex());
            chunk.readerIndex(chunk.readerIndex()+1);
            objects.add((JsonObject) converter.decode(chunk));
