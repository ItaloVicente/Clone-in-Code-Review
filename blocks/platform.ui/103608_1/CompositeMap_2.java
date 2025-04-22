		}
		for (K changedKey : diff.getChangedKeys()) {
			I oldValue1 = diff.getOldValue(changedKey);
			I newValue2 = diff.getNewValue(changedKey);
			boolean removed = firstMap.getKeys(oldValue1).isEmpty();
			boolean added = !rangeSet.contains(newValue2);
			if (removed) {
				pendingRemoves.put(oldValue1, changedKey);
				rangeSetRemovals.add(oldValue1);
