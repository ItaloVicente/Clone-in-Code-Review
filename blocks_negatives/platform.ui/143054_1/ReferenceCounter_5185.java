    private Map mapIdToRec = new HashMap(11);

    /**
     * Capture the information about an object.
     */
    public static class RefRec {
        public RefRec(Object id, Object value) {
            this.id = id;
            this.value = value;
            addRef();
        }

        public Object getId() {
            return id;
        }

        public Object getValue() {
            return value;
        }

        public int addRef() {
            ++refCount;
            return refCount;
        }

        public int removeRef() {
            --refCount;
            return refCount;
        }

        public int getRef() {
            return refCount;
        }

        public boolean isNotReferenced() {
            return (refCount <= 0);
        }

        public Object id;

        public Object value;

        private int refCount;
    }

    /**
     * Creates a new counter.
     */
    public ReferenceCounter() {
        super();
    }

    /**
     * Adds one reference to an object in the counter.
     *
     * @param id is a unique ID for the object.
     * @return the new ref count
     */
    public int addRef(Object id) {
        RefRec rec = (RefRec) mapIdToRec.get(id);
        if (rec == null) {
