	/**
	 * Restore the pre-3.4 filters.
	 *
	 * @param memento
	 */
	private void restoreLegacyFilters(IMemento memento) {
		IMemento[] sections = null;
		if (memento != null) {
			sections = memento.getChildren(TAG_LEGACY_FILTER_ENTRY);
		}

		for (IMemento child : sections) {
			String id = child.getString(IMemento.TAG_ID);
			if (id == null)
				continue;
			loadLegacyFilter(child);
		}
	}

