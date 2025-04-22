    /**
     * Creates a new adaptable list containing the given children.
     *
     * @param newChildren the list of children
     */
    public AdaptableList(IAdaptable[] newChildren) {
        this(newChildren.length);
        for (IAdaptable adaptable : newChildren) {
            children.add(adaptable);
        }
    }
