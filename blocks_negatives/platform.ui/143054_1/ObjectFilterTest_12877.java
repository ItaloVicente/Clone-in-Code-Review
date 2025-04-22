    /**
     * Returns whether the object filter correctly matches a given object.
     */
    private boolean preciselyMatches(Object object) {
        IActionFilter filter = Adapters.adapt(object, IActionFilter.class);
        if (filter == null) {
