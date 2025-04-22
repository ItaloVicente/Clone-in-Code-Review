	 * Creates a workbench preference dialog and selects particular preference
	 * page. If there is already a preference dialog open this dialog is used
	 * and its selection is set to the page with id preferencePageId. Show the
	 * other pages as filtered results using whatever filtering criteria the
	 * search uses. It is the responsibility of the caller to then call
	 * <code>open()</code>. The call to <code>open()</code> will not return
	 * until the dialog closes, so this is the last chance to manipulate the
	 * dialog.
