        provider = prov;
        this.part = part;
        readContributions(id, IWorkbenchRegistryConstants.TAG_VIEWER_CONTRIBUTION,
                IWorkbenchRegistryConstants.PL_POPUP_MENU);
        return (cache != null);
    }

    /**
     * Helper class to collect the menus and actions defined within a
     * contribution element.
     */
    private static class ViewerContribution extends BasicContribution implements ISelectionChangedListener {
        private ISelectionProvider selProvider;

        private ActionExpression visibilityTest;

        /**
         * Create a new ViewerContribution.
         *
         * @param selProvider the selection provider
         */
        public ViewerContribution(ISelectionProvider selProvider) {
            super();
            this.selProvider = selProvider;
