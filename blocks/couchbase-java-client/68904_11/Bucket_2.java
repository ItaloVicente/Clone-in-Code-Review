    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <V> boolean mapAdd(String docId, String key, V value);


    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <V> boolean mapAdd(String docId, String key, V value, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <V> boolean mapAdd(String docId, String key, V value, MutationOptionBuilder mutationOptionBuilder);


    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <V> boolean mapAdd(String docId, String key, V value, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <V> V mapGet(String docId, String key, Class<V> valueType);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <V> V mapGet(String docId, String key, Class<V> valueType, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    boolean mapRemove(String docId, String key);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    boolean mapRemove(String docId, String key, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    boolean mapRemove(String docId, String key, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    boolean mapRemove(String docId, String key, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    int mapSize(String docId);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    int mapSize(String docId, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> E listGet(String docId, int index, Class<E> elementType);


    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> E listGet(String docId, int index, Class<E> elementType, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean listPush(String docId, E element);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean listPush(String docId, E element, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean listPush(String docId, E element, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean listPush(String docId, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    boolean listRemove(String docId, int index);


    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    boolean listRemove(String docId, int index, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    boolean listRemove(String docId, int index, MutationOptionBuilder mutationOptionBuilder);


    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    boolean listRemove(String docId, int index, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean listShift(String docId, E element);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean listShift(String docId, E element, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean listShift(String docId, E element, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean listShift(String docId, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean listSet(String docId, int index, E element);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean listSet(String docId, int index, E element, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean listSet(String docId, int index, E element, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean listSet(String docId, int index, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    int listSize(String docId);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    int listSize(String docId, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean setAdd(String docId, E element);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean setAdd(String docId, E element, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean setAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean setAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean setExists(String docId, E element);


    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean setExists(String docId, E element, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> E setRemove(String docId, E element);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> E setRemove(String docId, E element, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> E setRemove(String docId, E element, MutationOptionBuilder mutationOptionBuilder);


    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> E setRemove(String docId, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    int setSize(String docId);


    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    int setSize(String docId, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean queueAdd(String docId, E element);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean queueAdd(String docId, E element, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean queueAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> boolean queueAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> E queueRemove(String docId, Class<E> elementType);


    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> E queueRemove(String docId, Class<E> elementType, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> E queueRemove(String docId, Class<E> elementType, MutationOptionBuilder mutationOptionBuilder);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    <E> E queueRemove(String docId, Class<E> elementType, MutationOptionBuilder mutationOptionBuilder, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    int queueSize(String docId);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    int queueSize(String docId, long timeout, TimeUnit timeUnit);

