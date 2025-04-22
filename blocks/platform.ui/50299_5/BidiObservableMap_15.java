		if (valuesToSingleKeys.containsKey(value)) {
			return Collections.singleton(valuesToSingleKeys.get(value));
		} else if (valuesToSetsOfKeys.containsKey(value)) {
			return Collections.unmodifiableSet(valuesToSetsOfKeys.get(value));
		} else {
			return Collections.emptySet();
		}
