    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    DocumentFragment<Lookup> retrieveIn(String docId, String... paths);

    @InterfaceStability.Experimental
    @InterfaceAudience.Public
    DocumentFragment<Lookup> retrieveIn(String docId, long timeout, TimeUnit timeUnit, String... paths);

