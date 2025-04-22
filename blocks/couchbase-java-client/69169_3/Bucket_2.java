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

