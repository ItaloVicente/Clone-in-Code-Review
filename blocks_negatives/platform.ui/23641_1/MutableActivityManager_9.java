    private Map identifiersById = new HashMap();
    
    /**
     * Avoid endless circular referencing of re-adding activity to evaluation
     * listener, because of adding it the first time to evaluation listener.
     */
    private boolean addingEvaluationListener = false;
    
    /**
     * A list of identifiers that need to have their activity sets reconciled in the background job.
     */
    private List deferredIdentifiers = Collections.synchronizedList(new LinkedList());
