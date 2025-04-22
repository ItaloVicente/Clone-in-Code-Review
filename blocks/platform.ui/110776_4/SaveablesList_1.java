	private void rememberRefKey(Saveable key) {
		ArrayList<Saveable> equals = new ArrayList<>();
		equals.add(key);
		equalKeys.put(key, equals);
	}

	private void incrementRefKeys(Saveable key) {
		Saveable keyUsedInCountMap = findExistingRefKey(key);
		if (keyUsedInCountMap == null) {
			rememberRefKey(key);
			return;
		}
		List<Saveable> equals = equalKeys.get(keyUsedInCountMap);
		equals.add(key);
		equalKeys.put(key, equals);
	}

