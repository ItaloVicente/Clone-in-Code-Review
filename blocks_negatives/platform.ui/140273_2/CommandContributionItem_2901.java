	 * @param serviceLocator
	 * 		a service locator that is most appropriate for this contribution.
	 * 		Typically the local {@link IWorkbenchWindow} or {@link
	 * 		IWorkbenchPartSite} will be sufficient.
	 * @param id
	 * 		The id for this item. May be <code>null</code>. Items without an id
	 * 		cannot be referenced later.
	 * @param commandId
	 * 		A command id for a defined command. Must not be <code>null</code>.
	 * @param parameters
	 * 		A map of strings to strings which represent parameter names to
	 * 		values. The parameter names must match those in the command
	 * 		definition.
	 * @param icon
	 * 		An icon for this item. May be <code>null</code>.
	 * @param disabledIcon
	 * 		A disabled icon for this item. May be <code>null</code>.
	 * @param hoverIcon
	 * 		A hover icon for this item. May be <code>null</code>.
	 * @param label
	 * 		A label for this item. May be <code>null</code>.
	 * @param mnemonic
	 * 		A mnemonic for this item to be applied to the label. May be
	 * 		<code>null</code>.
	 * @param tooltip
	 * 		A tooltip for this item. May be <code>null</code>. Tooltips are
	 * 		currently only valid for toolbar contributions.
	 * @param style
	 * 		The style of this menu contribution. See the STYLE_* contants.
