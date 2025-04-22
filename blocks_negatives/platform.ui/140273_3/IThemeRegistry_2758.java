    /**
     * A comparator that will sort IHierarchalThemeElementDefinition elements
     * by defaultsTo depth.
     *
     * @since 3.0
     */
    public static class HierarchyComparator implements Comparator {

        private IHierarchalThemeElementDefinition[] definitions;

        /**
         * Create a new comparator.
         *
         * @param definitions the elements to be sorted by depth, in ID order.
         */
        public HierarchyComparator(
                IHierarchalThemeElementDefinition[] definitions) {
            this.definitions = definitions;
        }

        @Override
