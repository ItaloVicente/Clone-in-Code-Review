    /**
     * Return the union of the parent and child key sets.
     *
     * @return the union.  This set is read only.
     */
    public Set keySet() {
        Set keySet = new HashSet(base.keySet());
        keySet.addAll(override.keySet());
        return Collections.unmodifiableSet(keySet);
    }
