			for (K changedKey : diff.getChangedKeys()) {
				I oldValue = diff.getOldValue(changedKey);
				I newValue = diff.getNewValue(changedKey);
				boolean removed = firstMap.getKeys(oldValue).isEmpty();
				boolean added = !rangeSet.contains(newValue);
				if (removed) {
					pendingRemoves.put(oldValue, changedKey);
					rangeSetRemovals.add(oldValue);
