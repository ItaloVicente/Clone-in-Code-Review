                public Observable<? extends Document<?>> call(EntityDocument<T> source) {
                    Document<?> converted = converter.fromEntity(source);
                    return bucket.upsert(converted, persistTo, replicateTo);
                }
            })
            .map(new Func1<Document<?>, EntityDocument<T>>() {
                @Override
                public EntityDocument<T> call(Document<?> stored) {
                    return EntityDocument.create(document.id(), document.expiry(), document.content(), stored.cas());
