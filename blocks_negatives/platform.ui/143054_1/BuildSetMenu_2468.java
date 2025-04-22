    /**
     * Fills the menu with Show View actions.
     * @param menu The menu being filled.
     */
    private void fillMenu(Menu menu) {
        boolean isAutoBuilding = ResourcesPlugin.getWorkspace()
                .isAutoBuilding();
