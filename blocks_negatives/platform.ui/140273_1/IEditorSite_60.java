    /**
     * <p>
     * Registers a pop-up menu with a particular id for extension. This method
     * should only be called if the target part has more than one context menu
     * to register.
     * </p>
     * <p>
     * By default, context menus include object contributions based on the
     * editor input for the current editor. It is possible to override this
     * behaviour by calling this method with <code>includeEditorInput</code>
     * as <code>false</code>. This might be desirable for editors that
     * present a localized view of an editor input (e.g., a node in a model
     * editor).
     * </p>
     * <p>
     * For a detailed description of context menu registration see
     * {@link IWorkbenchPartSite#registerContextMenu(MenuManager, ISelectionProvider)}
     * </p>
     *
     * @param menuId
     *            the menu id; must not be <code>null</code>.
     * @param menuManager
     *            the menu manager; must not be <code>null</code>.
     * @param selectionProvider
     *            the selection provider; must not be <code>null</code>.
     * @param includeEditorInput
     *            Whether the editor input should be included when adding object
     *            contributions to this context menu.
     * @see IWorkbenchPartSite#registerContextMenu(MenuManager,
     *      ISelectionProvider)
     * @since 3.1
     */
    void registerContextMenu(String menuId, MenuManager menuManager,
            ISelectionProvider selectionProvider, boolean includeEditorInput);
