    }

    /**
     * Empties the set
     */
    public void clear() {
        Object[] removed = data.toArray();
        data.clear();
        fireRemove(removed);
    }

    /**
     * Adds the given elements to the set
     *
     * @param toAdd elements to add
     */
    public void addAll(Object[] toAdd) {
    	Assert.isNotNull(toAdd);
        for (Object object : toAdd) {
            data.add(object);
        }

        fireAdd(toAdd);
    }

    /**
     * Adds the given elements to the set. Duplicate elements are ignored.
     *
     * @param toAdd elements to add
     */
    public void addAll(Collection toAdd) {
    	Assert.isNotNull(toAdd);
        addAll(toAdd.toArray());
    }

    /**
     * Fires a change notification for all elements in the given array
     *
     * @param changed array of elements that have changed
     */
    public void changeAll(Object[] changed) {
    	Assert.isNotNull(changed);
        fireUpdate(changed);
    }
