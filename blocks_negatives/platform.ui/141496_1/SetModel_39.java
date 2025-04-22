    /**
     * Removes all of the given elements from the set.
     *
     * @param toRemove elements to remove
     */
    public void removeAll(Object[] toRemove) {
    	Assert.isNotNull(toRemove);
        for (Object object : toRemove) {
            data.remove(object);
        }
