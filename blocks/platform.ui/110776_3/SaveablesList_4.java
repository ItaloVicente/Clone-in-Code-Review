	private long count(final Saveable keyToDecrement, Collection<Saveable> equals) {
		return equals.stream().filter(x -> x == keyToDecrement).count();
	}

	private Saveable fixKeyIfKnown(Saveable key) {
		Collection<Saveable> keys = equalKeys.get(key);
		if (keys == null) {
			return key;
		}
		Saveable goodKey = null;
		for (Saveable saveable : keys) {
			Integer refCount = modelRefCounts.get(saveable);
			if (refCount != null) {
				goodKey = saveable;
				break;
			}
		}
		if (goodKey == null) {
			return key;
		}
		return goodKey;
	}

	private void forgetRefKeys(Saveable key) {
		Collection<Saveable> keys = equalKeys.get(key);
		if (keys != null) {
			equalKeys.remove(key);
			keys.removeIf(x -> x == key);
		}
	}

	private void decrementRefKeys(Saveable key) {
		List<Saveable> keys = equalKeys.get(key);
		if (keys != null) {
			for (int i = 0; i < keys.size(); i++) {
				if (keys.get(i) == key) {
					keys.remove(i);
					break;
				}
			}
		}
	}

	private Saveable findExistingRefKey(Saveable key) {
		Saveable existingKey = null;
		Set<Saveable> keys = modelRefCounts.keySet();
		for (Saveable s : keys) {
			if (s.equals(key)) {
				existingKey = s;
				break;
			}
		}
		return existingKey;
	}

