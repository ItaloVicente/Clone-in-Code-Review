    /**
     * Creates a new viewer sorter, which uses the given collator
     * to sort strings.
     *
     * @param collator the collator to use to sort strings
     */
    public ViewerSorter(Collator collator) {
    	super(collator);
        this.collator = collator;
    }
