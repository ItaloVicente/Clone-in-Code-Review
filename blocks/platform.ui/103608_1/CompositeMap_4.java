		}
		for (K removedKey : diff.getRemovedKeys()) {
			I oldValue2 = diff.getOldValue(removedKey);
			if (firstMap.getKeys(oldValue2).isEmpty()) {
				pendingRemoves.put(oldValue2, removedKey);
				rangeSetRemovals.add(oldValue2);
			} else {
				removes.add(removedKey);
				oldValues.put(removedKey, secondMap.get(oldValue2));
				wrappedMap.remove(removedKey);
			}
		}
