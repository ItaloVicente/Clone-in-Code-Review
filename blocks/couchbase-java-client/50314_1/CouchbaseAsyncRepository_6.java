
    @Override
    public <T> Observable<T> insert(T document) {
        return insert(document, PersistTo.NONE, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<T> insert(T document, PersistTo persistTo) {
        return insert(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<T> insert(T document, ReplicateTo replicateTo) {
        return insert(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public <T> Observable<T> insert(final T document, final PersistTo persistTo, final ReplicateTo replicateTo) {
        return Observable
            .just(document)
            .flatMap(new Func1<T, Observable<? extends Document<?>>>() {
                @Override
                public Observable<? extends Document<?>> call(T source) {
                    Document<?> converted = converter.fromEntity(source);
                    return bucket.insert(converted, persistTo, replicateTo);
                }
            })
            .map(new Func1<Document<?>, T>() {
                @Override
                public T call(Document<?> stored) {
                    return document;
                }
            });
    }

    @Override
    public <T> Observable<T> replace(T document) {
        return replace(document, PersistTo.NONE, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<T> replace(T document, PersistTo persistTo) {
        return replace(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<T> replace(T document, ReplicateTo replicateTo) {
        return replace(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public <T> Observable<T> replace(final T document, final PersistTo persistTo, final ReplicateTo replicateTo) {
        return Observable
            .just(document)
            .flatMap(new Func1<T, Observable<? extends Document<?>>>() {
                @Override
                public Observable<? extends Document<?>> call(T source) {
                    Document<?> converted = converter.fromEntity(source);
                    return bucket.replace(converted, persistTo, replicateTo);
                }
            })
            .map(new Func1<Document<?>, T>() {
                @Override
                public T call(Document<?> stored) {
                    return document;
                }
            });
    }

    @Override
    public Observable<Boolean> exists(String id) {
        return bucket.exists(id);
    }

    @Override
    public <T> Observable<Boolean> exists(T document) {
        return Observable
            .just(document)
            .map(new Func1<T, String>() {
                @Override
                public String call(T source) {
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
    public <T> Observable<T> remove(T document) {
        return remove(document, PersistTo.NONE, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<T> remove(T document, PersistTo persistTo) {
        return remove(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <T> Observable<T> remove(T document, ReplicateTo replicateTo) {
        return remove(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public <T> Observable<T> remove(final T document, final PersistTo persistTo, final ReplicateTo replicateTo) {
        return Observable
            .just(document)
            .map(new Func1<T, String>() {
                @Override
                public String call(T source) {
                    Document<?> converted = converter.fromEntity(source);
                    return converted.id();
                }
            })
            .flatMap(new Func1<String, Observable<T>>() {
                @Override
                @SuppressWarnings("unchecked")
                public Observable<T> call(String id) {
                    return (Observable<T>) remove(id, persistTo, replicateTo, document.getClass());
                }
            });
    }

    @Override
    public <T> Observable<T> remove(String id, Class<T> documentClass) {
        return remove(id, PersistTo.NONE, ReplicateTo.NONE, documentClass);
    }

    @Override
    public <T> Observable<T> remove(String id, PersistTo persistTo, Class<T> documentClass) {
        return remove(id, persistTo, ReplicateTo.NONE, documentClass);
    }

    @Override
    public <T> Observable<T> remove(String id, ReplicateTo replicateTo, Class<T> documentClass) {
        return remove(id, PersistTo.NONE, replicateTo, documentClass);
    }

    @Override
    public <T> Observable<T> remove(String id, final PersistTo persistTo, final ReplicateTo replicateTo,
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

    class DocumentToType<T> implements Func1<JsonDocument, T> {

        private final Class<T> documentClass;

        public DocumentToType(Class<T> documentClass) {
            this.documentClass = documentClass;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T call(JsonDocument document) {
            return (T) converter.toEntity(document, documentClass);
        }
    }
