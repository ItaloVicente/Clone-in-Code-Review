    <T> Observable<DocumentFragment<T>> getIn(String id, String path, Class<T> fragmentType);
    Observable<Boolean> existsIn(String id, String path);

    <T> Observable<DocumentFragment<T>> upsertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);
    <T> Observable<DocumentFragment<T>> insertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);
    <T> Observable<DocumentFragment<T>> replaceIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo);

    <T> Observable<DocumentFragment<T>> extendIn(DocumentFragment<T> fragment, ExtendDirection direction, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);
    <T> Observable<DocumentFragment<T>> arrayInsertIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo);
    <T> Observable<DocumentFragment<T>> addUniqueIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);

    <T> Observable<DocumentFragment<T>> removeIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo);

    Observable<DocumentFragment<Long>> counterIn(DocumentFragment<Long> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);

    Observable<DocumentFragment<Object>> lookupIn(String id, LookupSpec... lookupSpecs);



