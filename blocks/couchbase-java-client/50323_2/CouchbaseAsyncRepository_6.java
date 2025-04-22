
    @Override
    public <T> Observable<EntityDocument<T>> replace(final EntityDocument<T> document) {
        return replace(document, PersistTo.NONE, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<EntityDocument<T>> replace(EntityDocument<T> document, PersistTo persistTo) {
        return replace(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<EntityDocument<T>> replace(EntityDocument<T> document, ReplicateTo replicateTo) {
        return replace(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public <T> Observable<EntityDocument<T>> replace(final EntityDocument<T> document, final PersistTo persistTo, final ReplicateTo replicateTo) {
        return Observable
            .just(document)
            .flatMap(new Func1<EntityDocument<T>, Observable<? extends Document<?>>>() {
                @Override
                public Observable<? extends Document<?>> call(EntityDocument<T> source) {
                    Document<?> converted = converter.fromEntity(source);
                    return bucket.replace(converted, persistTo, replicateTo);
                }
            })
            .map(new Func1<Document<?>, EntityDocument<T>>() {
                @Override
                public EntityDocument<T> call(Document<?> stored) {
                    return EntityDocument.create(document.id(), document.expiry(), document.content(), stored.cas());
                }
            });
    }

    @Override
    public Observable<Boolean> exists(String id) {
        return bucket.exists(id);
    }

    @Override
    public <T> Observable<Boolean> exists(EntityDocument<T> document) {
        return Observable
            .just(document)
            .map(new Func1<EntityDocument<T>, String>() {
                @Override
                public String call(EntityDocument<T> source) {
                    Document<?> converted = converter.fromEntity(source);
                    return converted.id();
                }
            })
            .flatMap(new Func1<String, Observable<Boolean>>() {
                @Override
                public Observable<Boolean> call(String id) {
                    return exists(id);
                }
            });
    }

    @Override
    public <T> Observable<EntityDocument<T>> remove(EntityDocument<T> document) {
        return remove(document, PersistTo.NONE, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<EntityDocument<T>> remove(EntityDocument<T> document, PersistTo persistTo) {
        return remove(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<EntityDocument<T>> remove(EntityDocument<T> document, ReplicateTo replicateTo) {
        return remove(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public <T> Observable<EntityDocument<T>> remove(final EntityDocument<T> document, final PersistTo persistTo, final ReplicateTo replicateTo) {
        return Observable
            .just(document)
            .map(new Func1<EntityDocument<T>, String>() {
                @Override
                public String call(EntityDocument<T> source) {
                    Document<?> converted = converter.fromEntity(source);
                    return converted.id();
                }
            })
            .flatMap(new Func1<String, Observable<EntityDocument<T>>>() {
                @Override
                @SuppressWarnings("unchecked")
                public Observable<EntityDocument<T>> call(String id) {
                    return remove(id, persistTo, replicateTo, (Class<T>) document.content().getClass());
                }
            });
    }

    @Override
    public <T> Observable<EntityDocument<T>> remove(String id, Class<T> documentClass) {
        return remove(id, PersistTo.NONE, ReplicateTo.NONE, documentClass);
    }

    @Override
    public <T> Observable<EntityDocument<T>> remove(String id, PersistTo persistTo, Class<T> documentClass) {
        return remove(id, persistTo, ReplicateTo.NONE, documentClass);
    }

    @Override
    public <T> Observable<EntityDocument<T>> remove(String id, ReplicateTo replicateTo, Class<T> documentClass) {
        return remove(id, PersistTo.NONE, replicateTo, documentClass);
    }

    @Override
    public <T> Observable<EntityDocument<T>> remove(String id, final PersistTo persistTo, final ReplicateTo replicateTo,
        Class<T> documentClass) {
        return Observable
            .just(id)
            .flatMap(new Func1<String, Observable<JsonDocument>>() {
                @Override
                public Observable<JsonDocument> call(String id) {
                    return bucket.remove(id, persistTo, replicateTo);
                }
            })
            .map(new DocumentToType<T>(documentClass));
    }

    class DocumentToType<T> implements Func1<JsonDocument, EntityDocument<T>> {

        private final Class<T> documentClass;

        public DocumentToType(Class<T> documentClass) {
            this.documentClass = documentClass;
        }

        @Override
        @SuppressWarnings("unchecked")
        public EntityDocument<T> call(JsonDocument document) {
            return converter.toEntity(document, documentClass);
        }
    }
