			Saveable keyUsedInCountMap;
			Collection<Saveable> equals = equalKeys.get(keyToDecrement);
			long instanceCount = count(keyToDecrement, equals);
			if (instanceCount == 1) {
				forgetRefKeys(keyToDecrement);
				keyUsedInCountMap = equals.iterator().next();
			} else {
				decrementRefKeys(keyToDecrement);
				keyUsedInCountMap = key;
			}
			modelRefCounts.remove(keyToDecrement);
			modelRefCounts.put(keyUsedInCountMap, Integer.valueOf(refCountValue - 1));
