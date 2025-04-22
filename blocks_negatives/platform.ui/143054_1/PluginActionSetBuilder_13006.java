    }

    /**
     * Helper class to collect the menus and actions defined within a
     * contribution element.
     */
    private static class ActionSetContribution extends BasicContribution {
        private String actionSetId;

        private WorkbenchWindow window;

        protected ArrayList adjunctActions = new ArrayList(0);

        /**
         * Create a new instance of <code>ActionSetContribution</code>.
         *
         * @param id the id
         * @param window the window to contribute to
         */
        public ActionSetContribution(String id, IWorkbenchWindow window) {
            super();
            actionSetId = id;
            this.window = (WorkbenchWindow) window;
        }

        /**
         * This implementation inserts the group into the action set additions group.
         */
        @Override
