    <T> Observable<DocumentFragment<T>> getIn(String id, String path, Class<T> fragmentType);
    Observable<Boolean> existsIn(String id, String path);

    Observable<DocumentFragment<Object>> lookupIn(String id, LookupSpec... lookupSpecs);



