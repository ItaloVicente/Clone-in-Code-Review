            .<InsertResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
                    return new InsertRequest(document.id(), encoded.value1(), document.expiry(), encoded.value2(), bucket);
                }
            })
