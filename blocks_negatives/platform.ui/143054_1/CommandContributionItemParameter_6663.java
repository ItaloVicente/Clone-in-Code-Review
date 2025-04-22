	 * @param serviceLocator
	 *            a service locator that is most appropriate for this
	 *            contribution. Typically the local {@link IWorkbenchWindow} or
	 *            {@link IWorkbenchPartSite} will be sufficient. Must not be
	 *            <code>null</code>.
	 * @param id
	 *            The id for this item. May be <code>null</code>. Items
	 *            without an id cannot be referenced later.
	 * @param commandId
	 *            A command id for a defined command. Must not be
	 *            <code>null</code>.
	 * @param style
	 *            The style of this menu contribution. See the STYLE_* contants.
	 */
	public CommandContributionItemParameter(IServiceLocator serviceLocator,
			String id, String commandId, int style) {
