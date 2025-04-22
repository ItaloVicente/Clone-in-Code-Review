				pendingChanges.remove(oldKey);
				pendingAdds.remove(addedKey);
				pendingRemoves.remove(oldKey);
				for (Iterator<K> it21 = elements1.iterator(); it21.hasNext();) {
					K element1 = it21.next();
					changes.add(element1);
					oldValues.put(element1, oldValue1);
					newValues.put(element1, newValue1);
					wrappedMap.put(element1, newValue1);
				}
			} else if (pendingAdds.remove(addedKey)) {
				for (Iterator<K> it22 = elements1.iterator(); it22.hasNext();) {
					K element2 = it22.next();
					adds.add(element2);
					newValues.put(element2, newValue1);
					wrappedMap.put(element2, newValue1);
