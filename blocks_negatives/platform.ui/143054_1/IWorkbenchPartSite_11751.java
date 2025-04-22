    /**
     * Registers a pop-up menu with the default id for extension.
     * The default id is defined as the part id.
     * <p>
     * Within the workbench one plug-in may extend the pop-up menus for a view
     * or editor within another plug-in.  In order to be eligible for extension,
     * the target part must publish each menu by calling <code>registerContextMenu</code>.
     * Once this has been done the workbench will automatically insert any action
     * extensions which exist.
     * </p>
     * <p>
     * A menu id must be provided for each registered menu.  For consistency across
     * parts the following strategy should be adopted by all part implementors.
     * </p>
     * <ol>
     *		<li>If the target part has only one context menu it should be registered
     *			with <code>id == part id</code>.  This can be done easily by calling
     *			<code>registerContextMenu(MenuManager, ISelectionProvider)</code>.
     *		<li>If the target part has more than one context menu a unique id should be
     *			defined for each.  Prefix each menu id with the part id and publish these
     *			ids within the javadoc for the target part.  Register each menu at
     *			runtime by calling <code>registerContextMenu(String, MenuManager,
     *			ISelectionProvider)</code>.  </li>
     * </ol>
     * <p>
     * Any pop-up menu which is registered with the workbench should also define a
     * <code>GroupMarker</code> in the registered menu with id
     * <code>IWorkbenchActionConstants.MB_ADDITIONS</code>.  Other plug-ins will use this
     * group as a reference point for insertion.  The marker should be defined at an
     * appropriate location within the menu for insertion.
     * </p>
     *
     * @param menuManager the menu manager
     * @param selectionProvider the selection provider
     */
    public void registerContextMenu(MenuManager menuManager,
            ISelectionProvider selectionProvider);
