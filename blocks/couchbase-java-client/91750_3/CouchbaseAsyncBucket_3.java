        return insert(document, (Span) null, timeout, timeUnit);
    }

    @SuppressWarnings({"unchecked"})
    private <D extends Document<?>> Observable<D> insert(D document, Span parent, long timeout, TimeUnit timeUnit) {
        final  Transcoder<Document<Object>, Object> transcoder =
            (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());
        return Mutate.insert(document, environment, transcoder, core, bucket, timeout, timeUnit, parent);
