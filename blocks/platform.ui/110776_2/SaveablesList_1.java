	private void rememberRefKey(Saveable key) {
		Set<Saveable> equals = new IdentitySet<>();
		equals.add(key);
		equalKeys.put(key, equals);
	}

	private void incrementRefKeys(Saveable key) {
		Saveable keyUsedInCountMap = findExistingRefKey(key);
		if (keyUsedInCountMap == null) {
			rememberRefKey(key);
			return;
		}
		if (key != keyUsedInCountMap) {
			Collection<Saveable> equals = equalKeys.get(keyUsedInCountMap);
			equals.add(key);
			equalKeys.put(key, equals);
		}
	}

