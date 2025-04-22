			}
			for (I changedKey : diff.getChangedKeys()) {
				Set<K> elements = firstMap.getKeys(changedKey);
				for (Iterator<K> it2 = elements.iterator(); it2.hasNext();) {
					K element = it2.next();
					changes.add(element);
					oldValues.put(element, diff.getOldValue(changedKey));
					V newValue = diff.getNewValue(changedKey);
					newValues.put(element, newValue);
					wrappedMap.put(element, newValue);
