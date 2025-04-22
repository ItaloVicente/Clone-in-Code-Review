	/**
	 *
	 * Overrides to set focus to the specific page if it a specific page was
	 * requested.
	 *
	 * @since 3.5
	 */
	@Override
	public int open() {
		IPreferencePage selectedPage = getCurrentPage();
		if ((initialPageId != null) && (selectedPage != null)) {
			Shell shell = getShell();
			if ((shell != null) && (!shell.isDisposed())) {
				Control control = selectedPage.getControl();
				if (!SwtUtil.isFocusAncestor(control))
					control.setFocus();
			}
		}
		return super.open();
	}

	/**
	 * Remembers the initial page ID
	 * @param pageId ID of the initial page to display
	 * @since 3.5
	 */
	public void setInitialPage(String pageId) {
		initialPageId = pageId;
	}

