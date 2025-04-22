    return core.<ViewQueryResponse>send(request).flatMap(new Func1<ViewQueryResponse, Observable<JsonObject>>() {
        @Override
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
            }
            return Observable.from(objects);
        }

        class MarkersProcessor implements ByteBufProcessor {
            List<Integer> markers = new ArrayList<Integer>();
            private int depth;
            private byte open = '{';
            private byte close = '}';
            private int counter;
