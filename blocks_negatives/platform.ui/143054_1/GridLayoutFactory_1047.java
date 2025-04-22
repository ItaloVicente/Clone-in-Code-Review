    /**
     * Creates a new GridLayout and attaches it to the given composite.
     * Does not create the GridData of any of the controls in the composite.
     *
     * @param c composite whose layout will be set
     * @see #generateLayout
     * @see #create
     * @see GridLayoutFactory
     */
    public void applyTo(Composite c) {
        c.setLayout(copyLayout(l));
    }
