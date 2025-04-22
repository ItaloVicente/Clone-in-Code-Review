			for (K removedKey : diff.getRemovedKeys()) {
				I oldValue = diff.getOldValue(removedKey);
				if (firstMap.getKeys(oldValue).isEmpty()) {
					pendingRemoves.put(oldValue, removedKey);
					rangeSetRemovals.add(oldValue);
				} else {
					removes.add(removedKey);
					oldValues.put(removedKey, secondMap.get(oldValue));
					wrappedMap.remove(removedKey);
				}
