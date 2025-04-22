        if (objectName == null) {
            objectName = object.toString();
        }
        return SelectionEnabler.verifyNameMatch(objectName, nameFilter);
    }

    /**
     * Helper class to collect the menus and actions defined within a
     * contribution element.
     */
    private static class ObjectContribution extends BasicContribution {
        private ObjectFilterTest filterTest;

        private ActionExpression visibilityTest;

        private Expression enablement;

        /**
         * Add a filter test.
         *
         * @param element the element
         */
        public void addFilterTest(IConfigurationElement element) {
            if (filterTest == null) {
