    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    DocumentFragment<Lookup> retrieveIn(String docId, String... paths);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    DocumentFragment<Lookup> retrieveIn(String docId, Collection<String> paths);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    DocumentFragment<Lookup> retrieveIn(String docId, Collection<String> paths, long timeout, TimeUnit timeUnit);

