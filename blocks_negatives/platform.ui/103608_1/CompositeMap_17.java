			for (I removedKey : removedKeys) {
				K element = pendingRemoves.remove(removedKey);
				if (element != null) {
					if (pendingChanges.containsKey(removedKey)) {
						Object newKey = pendingChanges.remove(removedKey);
						pendingChanges.remove(newKey);
						pendingAdds.remove(newKey);
						pendingRemoves.remove(removedKey);
						changes.add(element);
						oldValues.put(element, diff.getOldValue(removedKey));
						V newValue = secondMap.get(newKey);
						newValues.put(element, newValue);
						wrappedMap.put(element, newValue);
					} else {
						removes.add(element);
						V oldValue = diff.getOldValue(removedKey);
						oldValues.put(element, oldValue);
						wrappedMap.remove(element);
					}
