            .<AppendResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
                    return new AppendRequest(document.id(), document.cas(), encoded.value1(), bucket);
                }
            })
