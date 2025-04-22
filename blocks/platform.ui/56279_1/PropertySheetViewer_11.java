		}

		return getSortedEntries(getFilteredEntries(entry.getChildEntries()));
	}

	private List getChildren(PropertySheetCategory category) {
		return getSortedEntries(getFilteredEntries(category.getChildEntries()));
	}

	@Override
