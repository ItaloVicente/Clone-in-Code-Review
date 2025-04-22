		}
		for (I removedKey : removedKeys) {
			K element4 = pendingRemoves.remove(removedKey);
			if (element4 != null) {
				if (pendingChanges.containsKey(removedKey)) {
					Object newKey = pendingChanges.remove(removedKey);
					pendingChanges.remove(newKey);
					pendingAdds.remove(newKey);
					pendingRemoves.remove(removedKey);
					changes.add(element4);
					oldValues.put(element4, diff.getOldValue(removedKey));
					V newValue3 = secondMap.get(newKey);
					newValues.put(element4, newValue3);
					wrappedMap.put(element4, newValue3);
