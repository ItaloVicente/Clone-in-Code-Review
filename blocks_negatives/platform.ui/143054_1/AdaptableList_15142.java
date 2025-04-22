    protected List<IAdaptable> children = new ArrayList<>();

    /**
     * Creates a new adaptable list with the given children.
     */
    public AdaptableList() {
    }

    /**
     * Creates a new adaptable list with the given children.
     */
    public AdaptableList(IAdaptable[] newChildren) {
        for (int i = 0; i < newChildren.length; i++) {
            children.add(newChildren[i]);
        }
    }

    /**
     * Adds all the adaptable objects in the given enumeration to this list.
     * Returns this list.
     */
    public AdaptableList add(Iterator<IAdaptable> iterator) {
        while (iterator.hasNext()) {
            add(iterator.next());
        }
        return this;
    }

    /**
     * Adds the given adaptable object to this list.
     * Returns this list.
     */
    public AdaptableList add(IAdaptable adaptable) {
        children.add(adaptable);
        return this;
    }

    @SuppressWarnings("unchecked")
