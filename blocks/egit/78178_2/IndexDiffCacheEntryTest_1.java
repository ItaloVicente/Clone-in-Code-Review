	private boolean containsItemsStartingWith(Collection<String> values,
			String prefix) {
		for (String item : values) {
			if (item.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

