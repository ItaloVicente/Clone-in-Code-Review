    /**
     * Returns one of the return code constants declared on this interface,
     * indicating whether the entity represented by the passed String should be overwritten.
     * <p>
     * This method may be called from a non-UI thread, in which case this method must run the query
     * in a sync exec in the UI thread, if it needs to query the user.
     * </p>
     * @param pathString the path representing the entity to be overwritten
     * @return one of the return code constants declared on this interface
     */
    String queryOverwrite(String pathString);
