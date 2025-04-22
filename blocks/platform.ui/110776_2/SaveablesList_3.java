			Saveable keyUsedInCountMap;
			Collection<Saveable> equals = equalKeys.get(keyToDecrement);
			if (equals.size() > 1) {
				decrementRefKeys(keyToDecrement);
				if (!equals.contains(keyToDecrement)) {
					equalKeys.remove(keyToDecrement);
				}
				keyUsedInCountMap = equals.iterator().next();
			} else {
				keyUsedInCountMap = key;
			}
			modelRefCounts.remove(keyToDecrement);
			modelRefCounts.put(keyUsedInCountMap, Integer.valueOf(refCount.intValue() - 1));
