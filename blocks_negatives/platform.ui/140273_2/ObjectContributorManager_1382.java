     * Adds contributors for the given types to the result list.
     */
    private void addContributorsFor(List types, List result) {
        for (Iterator classes = types.iterator(); classes.hasNext();) {
            Class clazz = (Class) classes.next();
            List contributorList = (List) contributors.get(clazz.getName());
            if (contributorList != null) {
