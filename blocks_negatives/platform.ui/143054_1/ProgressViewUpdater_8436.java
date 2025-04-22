    /**
     * The UpdatesInfo is a private class for keeping track of the updates
     * required.
     */
    static class UpdatesInfo {

        Collection<JobTreeElement> additions = new HashSet<>();

        Collection<JobTreeElement> deletions = new HashSet<>();

        Collection<JobTreeElement> refreshes = new HashSet<>();

        boolean updateAll = false;

        private UpdatesInfo() {
        }

        /**
         * Add an add update
         *
         * @param addition
         */
        void add(JobTreeElement addition) {
            additions.add(addition);
        }

        /**
         * Add a remove update
         *
         * @param removal
         */
        void remove(JobTreeElement removal) {
            deletions.add(removal);
        }

        /**
         * Add a refresh update
         *
         * @param refresh
         */
        void refresh(JobTreeElement refresh) {
            refreshes.add(refresh);
        }

        /**
         * Reset the caches after completion of an update.
         */
        void reset() {
            additions.clear();
            deletions.clear();
            refreshes.clear();
            updateAll = false;
        }

        void processForUpdate() {
            HashSet<JobTreeElement> staleAdditions = new HashSet<>();

            Iterator<JobTreeElement> additionsIterator = additions.iterator();
            while (additionsIterator.hasNext()) {
