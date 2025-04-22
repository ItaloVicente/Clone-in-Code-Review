    <T> Observable<DocumentFragment<T>> getIn(String id, String path, Class<T> fragmentType);
    Observable<Boolean> existsIn(String id, String path);
    <T> Observable<DocumentFragment<T>> upsertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);

    Observable<DocumentFragment<Object>> lookupIn(String id, LookupSpec... lookupSpecs);



