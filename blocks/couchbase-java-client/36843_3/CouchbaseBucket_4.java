
  @Override
  @SuppressWarnings("unchecked")
  public <D extends Document<?>> Observable<D> remove(final D document) {
      final Converter<?, Object> converter = (Converter<?, Object>) converters.get(document.getClass());
    RemoveRequest request = new RemoveRequest(document.id(), document.cas(),
      bucket);
    return core.<RemoveResponse>send(request).map(new Func1<RemoveResponse, D>() {
      @Override
      public D call(RemoveResponse response) {
          return (D) converter.newDocument(document.id(), document.content(), document.cas(), document.expiry(),
              response.status());
      }
    });
  }

  @Override
  public Observable<JsonDocument> remove(final String id) {
    return remove(id, JsonDocument.class);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <D extends Document<?>> Observable<D> remove(final String id, final Class<D> target) {
    Converter<?, ?> converter = converters.get(target);
    return remove((D) converter.newDocument(id, null, 0, 0, null));
  }

  @Override
  public Observable<ViewRow> query(final ViewQuery query) {
    final ViewQueryRequest request = new ViewQueryRequest(query.design(), query.view(), query.development(), bucket, password);

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
            @Override
            public boolean process(byte value) throws Exception {
                counter++;
                if (value == open) {
                    depth++;
                }
                if (value == close) {
                    depth--;
                    if (depth == 0) {
                        markers.add(counter);
                    }
                }
                return true;
            }

            public List<Integer> markers() {
                return markers;
            }
        }
    }).map(new Func1<JsonObject, ViewRow>() {
        @Override
        public ViewRow call(JsonObject object) {
            return new ViewRow(object.getString("id"), object.getString("key"), object.get("value"));
        }
    });
  }

