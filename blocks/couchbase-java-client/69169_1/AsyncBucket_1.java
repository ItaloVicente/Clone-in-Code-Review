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

