			return compare(getDefaultsTo(def0), getDefaultsTo(def1));
		}

		private IHierarchalThemeElementDefinition getDefaultsTo(String id) {
			int idx = Arrays.binarySearch(definitions, id, ID_COMPARATOR);
			if (idx >= 0) {
