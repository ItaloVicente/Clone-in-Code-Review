	/**
	 * Returns the quick access shell for testing. Should not be referenced outside
	 * of the tests.
	 *
	 * @return the current quick access shell or <code>null</code>
	 */
	public Shell getQuickAccessShell() {
		createContentsAndTable();
		return shell;
	}

	/**
	 * Returns the quick access search text for testing. Should not be referenced
	 * outside of the tests.
	 *
	 * @return the search text in the workbench window or <code>null</code>
	 */
