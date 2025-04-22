    /**
     * Creates a new GridLayout, and initializes it with values from the factory.
     *
     * @return a new initialized GridLayout.
     * @see #applyTo
     */
    public GridLayout create() {
        return copyLayout(l);
    }
