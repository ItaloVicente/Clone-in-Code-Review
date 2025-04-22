    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    DocumentFragment<Lookup> retrieveIn(String docId, String... paths);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    DocumentFragment<Lookup> retrieveIn(String docId, List<String> paths);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    DocumentFragment<Lookup> retrieveIn(String docId, List<String> paths, long timeout, TimeUnit timeUnit);

