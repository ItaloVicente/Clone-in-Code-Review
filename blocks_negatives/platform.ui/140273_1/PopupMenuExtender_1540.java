    /**
     * Construct a new menu extender.
     *
     * @param id
     *            the menu id
     * @param menu
     *            the menu to extend
     * @param prov
     *            the selection provider
     * @param part
     *            the part to extend
	 * @param context
	 *            the context to create the child popup menu context under
     * @param includeEditorInput
     *            Whether the editor input should be included when adding object
     *            contributions to this context menu.
     */
	public PopupMenuExtender(final String id, final MenuManager menu,
			final ISelectionProvider prov, final IWorkbenchPart part, IEclipseContext context,
			final boolean includeEditorInput) {
