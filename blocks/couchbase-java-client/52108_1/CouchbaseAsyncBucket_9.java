        final  Transcoder<Document<Object>, Object> transcoder =
            (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());
        return core.<ReplaceResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
                    return new ReplaceRequest(document.id(), encoded.value1(), document.cas(), document.expiry(),
                        encoded.value2(), bucket);
                }
            })
