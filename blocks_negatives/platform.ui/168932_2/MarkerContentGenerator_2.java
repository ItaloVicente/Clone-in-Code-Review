	/**
	 * Load the legacy filter into the system.
	 *
	 * @param child
	 */
	private void loadLegacyFilter(IMemento child) {
		MarkerFieldFilterGroup newGroup = new MarkerFieldFilterGroup(null, this);
		newGroup.legacyLoadSettings(child);
		getAllFilters().add(newGroup);
	}

	/**
	 * Load the pre-3.4 filters.
	 *
	 * @param mementoString
	 */
	private void loadLegacyFiltersFrom(String mementoString) {
		if (mementoString.equals(IPreferenceStore.STRING_DEFAULT_DEFAULT)) {
			return;
		}
		IMemento memento;
		try {
			memento = XMLMemento.createReadRoot(new StringReader(mementoString));
			restoreLegacyFilters(memento);
		} catch (WorkbenchException e) {
			StatusManager.getManager().handle(e.getStatus());
			return;
		}
	}
