    @Override
    public <D extends Document<?>> D replace(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .replace(document, persistTo, replicateTo)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D replace(D document, PersistTo persistTo) {
        return replace(document, persistTo, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D replace(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .replace(document, persistTo)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D replace(D document, ReplicateTo replicateTo) {
        return replace(document, replicateTo, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D replace(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .replace(document, replicateTo)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D remove(D document) {
        return remove(document, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D remove(D document, PersistTo persistTo, ReplicateTo replicateTo) {
        return remove(document, persistTo, replicateTo, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D remove(D document, PersistTo persistTo) {
        return remove(document, persistTo, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D remove(D document, ReplicateTo replicateTo) {
        return remove(document, replicateTo, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D remove(D document, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .remove(document)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D remove(D document, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .remove(document, persistTo, replicateTo)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D remove(D document, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .remove(document, persistTo)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D remove(D document, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .remove(document, replicateTo)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public JsonDocument remove(String id) {
        return remove(id, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public JsonDocument remove(String id, PersistTo persistTo, ReplicateTo replicateTo) {
        return remove(id, persistTo, replicateTo, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public JsonDocument remove(String id, PersistTo persistTo) {
        return remove(id, persistTo, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public JsonDocument remove(String id, ReplicateTo replicateTo) {
        return remove(id, replicateTo, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public JsonDocument remove(String id, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .remove(id)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public JsonDocument remove(String id, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .remove(id, persistTo, replicateTo)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public JsonDocument remove(String id, PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .remove(id, persistTo)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public JsonDocument remove(String id, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .remove(id, replicateTo)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D remove(String id, Class<D> target) {
        return remove(id, target, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D remove(String id, PersistTo persistTo, ReplicateTo replicateTo, Class<D> target) {
        return remove(id, persistTo, replicateTo, target, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D remove(String id, PersistTo persistTo, Class<D> target) {
        return remove(id, persistTo, target, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D remove(String id, ReplicateTo replicateTo, Class<D> target) {
        return remove(id, replicateTo, target, binaryTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <D extends Document<?>> D remove(String id, Class<D> target, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .remove(id, target)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D remove(String id, PersistTo persistTo, ReplicateTo replicateTo, Class<D> target, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .remove(id, persistTo, replicateTo, target)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D remove(String id, PersistTo persistTo, Class<D> target, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .remove(id, persistTo, target)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public <D extends Document<?>> D remove(String id, ReplicateTo replicateTo, Class<D> target, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .remove(id, replicateTo, target)
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }

    @Override
    public ViewResult query(ViewQuery query) {
        return query(query, environment.viewTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public QueryResult query(Query query) {
        return query(query, environment.queryTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public QueryResult query(String query) {
        return query(query, environment.queryTimeout(), TIMEOUT_UNIT);
    }

    @Override
    public ViewResult query(ViewQuery query, long timeout, TimeUnit timeUnit) {
        return asyncBucket
            .query(query)
            .map(new Func1<AsyncViewResult, ViewResult>() {
                @Override
                public ViewResult call(AsyncViewResult asyncViewResult) {
                    return new DefaultViewResult(environment, CouchbaseBucket.this,
                        asyncViewResult.rows(), asyncViewResult.totalRows(), asyncViewResult.success(),
                        asyncViewResult.error(), asyncViewResult.debug());
