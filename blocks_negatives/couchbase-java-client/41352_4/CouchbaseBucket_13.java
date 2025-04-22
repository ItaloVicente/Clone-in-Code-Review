    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> insert(final D document) {
        final  Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());
        Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
        return core
            .<InsertResponse>send(new InsertRequest(document.id(), encoded.value1(), document.expiry(), encoded.value2(), bucket))
            .flatMap(new Func1<InsertResponse, Observable<? extends D>>() {
                @Override
                public Observable<? extends D> call(InsertResponse response) {
                    if (response.status() == ResponseStatus.EXISTS) {
                        return Observable.error(new DocumentAlreadyExistsException());
                    }
                    return Observable.just((D) transcoder.newDocument(document.id(), document.expiry(), document.content(), response.cas()));
                }
            });
    }

    @Override
    public <D extends Document<?>> Observable<D> insert(final D document, final PersistTo persistTo,
        final ReplicateTo replicateTo) {
        return insert(document).flatMap(new Func1<D, Observable<D>>() {
            @Override
            public Observable<D> call(final D doc) {
                return Observe
                    .call(core, bucket, doc.id(), doc.cas(), false, persistTo, replicateTo)
                    .map(new Func1<Boolean, D>() {
                        @Override
                        public D call(Boolean aBoolean) {
                            return doc;
                        }
                    }).onErrorResumeNext(new Func1<Throwable, Observable<? extends D>>() {
                        @Override
                        public Observable<? extends D> call(Throwable throwable) {
                            return Observable.error(new DurabilityException("Durability constraint failed.", throwable));
                        }
                    });
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D extends Document<?>> Observable<D> upsert(final D document) {
        final  Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());
        Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
        return core
            .<UpsertResponse>send(new UpsertRequest(document.id(), encoded.value1(), document.expiry(), encoded.value2(), bucket))
            .flatMap(new Func1<UpsertResponse, Observable<D>>() {
                @Override
                public Observable<D> call(UpsertResponse response) {
                    if (response.status() == ResponseStatus.EXISTS) {
                        return Observable.error(new CASMismatchException());
                    }
                    return Observable.just((D) transcoder.newDocument(document.id(), document.expiry(), document.content(), response.cas()));
                }
            });
    }

    @Override
    public <D extends Document<?>> Observable<D> upsert(final D document, final PersistTo persistTo, final ReplicateTo replicateTo) {
        return upsert(document).flatMap(new Func1<D, Observable<D>>() {
            @Override
            public Observable<D> call(final D doc) {
                return Observe
                    .call(core, bucket, doc.id(), doc.cas(), false, persistTo, replicateTo)
                    .map(new Func1<Boolean, D>() {
                        @Override
                        public D call(Boolean aBoolean) {
                            return doc;
                        }
                    });
            }
        });
    }

    @Override
  @SuppressWarnings("unchecked")
  public <D extends Document<?>> Observable<D> replace(final D document) {
        final  Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());
        Tuple2<ByteBuf, Integer> encoded = transcoder.encode((Document<Object>) document);
    return core.<ReplaceResponse>send(new ReplaceRequest(document.id(), encoded.value1(), document.cas(), document.expiry(), encoded.value2(), bucket))

        .flatMap(new Func1<ReplaceResponse, Observable<D>>() {
            @Override
            public Observable<D> call(ReplaceResponse response) {
                if (response.status() == ResponseStatus.NOT_EXISTS) {
                    return Observable.error(new DocumentDoesNotExistException());
                }
                if (response.status() == ResponseStatus.EXISTS) {
                    return Observable.error(new CASMismatchException());
                }
                return Observable.just((D) transcoder.newDocument(document.id(), document.expiry(), document.content(), response.cas()));
            }
        });
  }
