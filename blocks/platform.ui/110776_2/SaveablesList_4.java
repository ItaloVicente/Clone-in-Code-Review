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
			for (Saveable saveable : keys) {
				equalKeys.remove(saveable);
			}
		}
	}

	private void decrementRefKeys(Saveable key) {
		Collection<Saveable> keys = equalKeys.get(key);
		if (keys != null) {
			keys.remove(key);
			if (keys.isEmpty()) {
				equalKeys.remove(key);
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

