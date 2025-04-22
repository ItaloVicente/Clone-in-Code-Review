				pendingChanges.remove(oldKey);
				pendingAdds.remove(addedKey);
				pendingRemoves.remove(oldKey);
				for (K element1 : elements1) {
					changes.add(element1);
					oldValues.put(element1, oldValue1);
					newValues.put(element1, newValue1);
					wrappedMap.put(element1, newValue1);
