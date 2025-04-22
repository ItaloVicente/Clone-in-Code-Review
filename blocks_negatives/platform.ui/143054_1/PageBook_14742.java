    /**
     * Creates a new empty pagebook.
     *
     * @param parent the parent composite
     * @param style the SWT style bits
     */
    public PageBook(Composite parent, int style) {
        super(parent, style);
        setLayout(new PageBookLayout());
    }
