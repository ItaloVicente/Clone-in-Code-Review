			}
			for (I changedKey : diff.getChangedKeys()) {
				Set<K> elements = firstMap.getKeys(changedKey);
				for (K element : elements) {
					changes.add(element);
					oldValues.put(element, diff.getOldValue(changedKey));
					V newValue = diff.getNewValue(changedKey);
					newValues.put(element, newValue);
					wrappedMap.put(element, newValue);
