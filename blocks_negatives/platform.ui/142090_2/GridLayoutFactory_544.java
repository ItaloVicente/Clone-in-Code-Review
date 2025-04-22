    /**
     * Creates a factory that creates copies of the given layout.
     *
     * @param l layout to copy
     * @return a new GridLayoutFactory instance that creates copies of the given layout
     */
    public static GridLayoutFactory createFrom(GridLayout l) {
    	return new GridLayoutFactory(copyLayout(l));
    }
