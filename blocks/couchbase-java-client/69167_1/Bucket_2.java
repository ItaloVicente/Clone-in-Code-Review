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

