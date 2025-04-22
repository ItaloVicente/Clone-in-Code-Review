            return compare(getDefaultsTo(def0), getDefaultsTo(def1));
        }

        /**
         * @param id the identifier to search for.
         * @return the <code>IHierarchalThemeElementDefinition</code> that
         * matches the id.
         */
        private IHierarchalThemeElementDefinition getDefaultsTo(String id) {
            int idx = Arrays.binarySearch(definitions, id, ID_COMPARATOR);
            if (idx >= 0) {
