        return ReplicaReader
            .read(core, id, type, bucket)
            .map(new Func1<GetResponse, D>() {
                @Override
                public D call(final GetResponse response) {
                    Transcoder<?, Object> transcoder = (Transcoder<?, Object>) transcoders.get(target);
                    return (D) transcoder.decode(id, response.content(), response.cas(), 0, response.flags(),
                        response.status());
                }
            })
            .cacheWithInitialCapacity(type.maxAffectedNodes());
