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

