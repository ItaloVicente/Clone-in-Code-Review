    private static final long serialVersionUID = 8714236818791036721L;

    /**
     * Create a new NullElementPointer.
     * @param parent parent pointer
     * @param index int
     */
    public NullElementPointer(NodePointer parent, int index) {
        super(parent, (Object) null);
        this.index = index;
    }

    @Override
