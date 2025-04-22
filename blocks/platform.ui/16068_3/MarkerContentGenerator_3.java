	private void loadSupressSymbolicLinkSettings(IMemento memento) {
		if (memento == null)
			return;

		Boolean suppressSymbolicDuplicatesChecked = memento.getBoolean(TAG_MARKER_SYMBOLIC_LINKS_CHECKED);
		if (suppressSymbolicDuplicatesChecked != null) {
			markerSuppressLinksChecked = suppressSymbolicDuplicatesChecked.booleanValue();
		}
	}

