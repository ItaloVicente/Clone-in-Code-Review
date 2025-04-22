	 * @param shell
	 *            The Shell to parent the dialog off of if it is not already
	 *            created. May be <code>null</code> in which case the active
	 *            workbench window will be used if available.
	 * @param preferencePageId
	 *            The identifier of the preference page to open; may be
	 *            <code>null</code>. If it is <code>null</code>, then the
	 *            preference page is not selected or modified in any way.
	 * @param displayedIds
	 *            The ids of the other pages to be displayed using the same
	 *            filtering criterea as search. If this is <code>null</code>,
	 *            then the all preference pages are shown.
	 * @param data
	 *            Data that will be passed to all of the preference pages to be
	 *            applied as specified within the page as they are created. If
	 *            the data is <code>null</code> nothing will be called.
