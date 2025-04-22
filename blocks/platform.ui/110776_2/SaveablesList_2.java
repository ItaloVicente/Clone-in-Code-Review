		Integer refCount = modelRefCounts.get(key);
		final Saveable keyToDecrement = key;
		if (refCount == null) {
			key = fixKeyIfKnown(key);
			if (keyToDecrement != key) {
				refCount = modelRefCounts.get(key);
			}
		}
		if (refCount == null) {
			Assert.isTrue(false, keyToDecrement + ": " + keyToDecrement.getName()); //$NON-NLS-1$
		}
