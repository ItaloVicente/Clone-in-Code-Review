    public <D extends Document<?>> Observable<D> replace(final D document, final PersistTo persistTo, final ReplicateTo replicateTo) {
        return replace(document).flatMap(new Func1<D, Observable<D>>() {
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
