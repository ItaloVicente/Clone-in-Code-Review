    /**
     * Creates a new viewer sorter, which uses the default collator
     * to sort strings.
     */
    public ViewerSorter() {
        this(Collator.getInstance());
    }
