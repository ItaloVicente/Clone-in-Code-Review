    <T> DocumentFragment<T> getIn(String id, String path, Class<T> fragmentType);
    <T> DocumentFragment<T> getIn(String id, String path, Class<T> fragmentType, long timeout, TimeUnit timeUnit);
    boolean existsIn(String id, String path);
    boolean existsIn(String id, String path, long timeout, TimeUnit timeUnit);
    <T> DocumentFragment<T> upsertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);
    <T> DocumentFragment<T> upsertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);



    List<DocumentFragment<Object>> lookupIn(String id, LookupSpec... lookupSpecs);
    List<DocumentFragment<Object>> lookupIn(String id, long timeout, TimeUnit timeUnit, LookupSpec... lookupSpecs);


