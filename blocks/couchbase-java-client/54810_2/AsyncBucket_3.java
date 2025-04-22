    <D extends Document<?>> Observable<D> append(D document, PersistTo persistTo);

    <D extends Document<?>> Observable<D> append(D document, ReplicateTo replicateTo);

    <D extends Document<?>> Observable<D> append(D document, PersistTo persistTo, ReplicateTo replicateTo);

