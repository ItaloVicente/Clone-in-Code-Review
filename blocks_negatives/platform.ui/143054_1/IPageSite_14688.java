    /**
     * Registers a pop-up menu with a particular id for extension.
     * <p>
     * Within the workbench one plug-in may extend the pop-up menus for a view
     * or editor within another plug-in.  In order to be eligible for extension,
     * the menu must be registered by calling <code>registerContextMenu</code>.
     * Once this has been done the workbench will automatically insert any action
     * extensions which exist.
     * </p>
     * <p>
     * A unique menu id must be provided for each registered menu. This id should
     * be published in the Javadoc for the page.
     * </p>
     * <p>
     * Any pop-up menu which is registered with the workbench should also define a
     * <code>GroupMarker</code> in the registered menu with id
     * <code>IWorkbenchActionConstants.MB_ADDITIONS</code>.  Other plug-ins will use this
     * group as a reference point for insertion.  The marker should be defined at an
     * appropriate location within the menu for insertion.
     * </p>
     *
     * @param menuId the menu id
     * @param menuManager the menu manager
     * @param selectionProvider the selection provider
     */
    public void registerContextMenu(String menuId, MenuManager menuManager,
            ISelectionProvider selectionProvider);
