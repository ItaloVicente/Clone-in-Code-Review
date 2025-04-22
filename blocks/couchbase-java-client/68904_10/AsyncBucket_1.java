    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <V> Observable<Boolean> mapAdd(String docId, String key, V value);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <V> Observable<Boolean> mapAdd(String docId, String key, V value, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <V> Observable<V> mapGet(String docId, String key, Class<V> valueType);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    Observable<Boolean> mapRemove(String docId, String key);


    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    Observable<Boolean> mapRemove(String docId, String key, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    Observable<Integer> mapSize(String docId);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<E> listGet(String docId, int index, Class<E> elementType);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<Boolean> listPush(String docId, E element);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<Boolean> listPush(String docId, E element, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    Observable<Boolean> listRemove(String docId, int index);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    Observable<Boolean> listRemove(String docId, int index, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<Boolean> listShift(String docId, E element);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<Boolean> listShift(String docId, E element, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<Boolean> listSet(String docId, int index, E element);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<Boolean> listSet(String docId, int index, E element, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    Observable<Integer> listSize(String docId);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<Boolean> setAdd(String docId, E element);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<Boolean> setAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<Boolean> setExists(String docId, E element);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<E> setRemove(String docId, E element);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<E> setRemove(String docId, E element, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    Observable<Integer> setSize(String docId);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<Boolean> queueAdd(String docId, E element);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<Boolean> queueAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<E> queueRemove(String docId, Class<E> elementType);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> Observable<E> queueRemove(String docId, Class<E> elementType, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    Observable<Integer> queueSize(String docId);

