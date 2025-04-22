			for (K changedKey : diff.getChangedKeys()) {
				I oldValue = diff.getOldValue(changedKey);
				I newValue = diff.getNewValue(changedKey);
				boolean removed = firstMap.getKeys(oldValue).isEmpty();
				boolean added = !rangeSet.contains(newValue);
				if (removed) {
					pendingRemoves.put(oldValue, changedKey);
					rangeSetRemovals.add(oldValue);
				}
				if (added) {
					pendingAdds.add(newValue);
					rangeSetAdditions.add(newValue);
				}
				if (added || removed) {
					pendingChanges.put(oldValue, newValue);
					pendingChanges.put(newValue, oldValue);
				} else {
					changes.add(changedKey);
					oldValues.put(changedKey, secondMap.get(oldValue));
					wrappedMap.put(changedKey, secondMap.get(newValue));
				}
