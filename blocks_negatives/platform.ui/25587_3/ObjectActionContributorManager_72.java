	 * Contributes submenus and/or actions applicable to the selection in the
	 * provided viewer into the provided popup menu.
	 * 
	 * @param part
	 *            the part being contributed to
	 * @param popupMenu
	 *            the menu being contributed to
	 * @param selProv
	 *            the selection provider
	 * @param alreadyContributed
	 *            the set of contributors that already contributed to the menu
	 * @return whether anything was contributed
	 */
	public boolean contributeObjectActions(IWorkbenchPart part, IMenuManager popupMenu,
			ISelectionProvider selProv, Set<IObjectActionContributor> alreadyContributed) {
