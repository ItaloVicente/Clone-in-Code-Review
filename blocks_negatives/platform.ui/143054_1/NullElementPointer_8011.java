    /**
     * Create a new NullElementPointer.
     * @param parent parent pointer
     * @param index int
     */
    public NullElementPointer(NodePointer parent, int index) {
        super(parent, (Object) null);
        this.index = index;
    }
