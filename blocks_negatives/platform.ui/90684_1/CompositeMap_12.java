				if (added || removed) {
					pendingChanges.put(oldValue, newValue);
					pendingChanges.put(newValue, oldValue);
				} else {
					changes.add(changedKey);
					oldValues.put(changedKey, secondMap.get(oldValue));
					wrappedMap.put(changedKey, secondMap.get(newValue));
