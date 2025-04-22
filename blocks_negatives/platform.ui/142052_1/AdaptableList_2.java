        return null;
    }

    /**
     * Removes the given adaptable object from this list.
     */
    public void remove(IAdaptable adaptable) {
        children.remove(adaptable);
    }

    /**
     * Returns the number of items in the list
     */
    public int size() {
        return children.size();
    }
