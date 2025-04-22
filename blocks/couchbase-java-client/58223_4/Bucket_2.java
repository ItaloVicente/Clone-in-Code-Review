    <T> DocumentFragment<T> getIn(String id, String path, Class<T> fragmentType);
    <T> DocumentFragment<T> getIn(String id, String path, Class<T> fragmentType, long timeout, TimeUnit timeUnit);
    boolean existsIn(String id, String path);
    boolean existsIn(String id, String path, long timeout, TimeUnit timeUnit);

    <T> DocumentFragment<T> upsertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);
    <T> DocumentFragment<T> upsertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);
    <T> DocumentFragment<T> insertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);
    <T> DocumentFragment<T> insertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);
    <T> DocumentFragment<T> replaceIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo);
    <T> DocumentFragment<T> replaceIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    <T> DocumentFragment<T> extendIn(DocumentFragment<T> fragment, ExtendDirection direction, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);
    <T> DocumentFragment<T> extendIn(DocumentFragment<T> fragment, ExtendDirection direction, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);
    <T> DocumentFragment<T> arrayInsertIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo);
    <T> DocumentFragment<T> arrayInsertIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);
    <T> DocumentFragment<T> addUniqueIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);
    <T> DocumentFragment<T> addUniqueIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    <T> DocumentFragment<T> removeIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo);
    <T> DocumentFragment<T> removeIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    DocumentFragment<Long> counterIn(DocumentFragment<Long> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);
    DocumentFragment<Long> counterIn(DocumentFragment<Long> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    DocumentFragment<List<LookupResult>> lookupIn(String id, long timeout, TimeUnit timeUnit, LookupSpec... lookupSpecs);
    DocumentFragment<List<LookupResult>> lookupIn(String id, LookupSpec... lookupSpecs);


