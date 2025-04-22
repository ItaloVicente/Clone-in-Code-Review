			} else if (pendingAdds.remove(addedKey)) {
				for (K element2 : elements1) {
					adds.add(element2);
					newValues.put(element2, newValue1);
					wrappedMap.put(element2, newValue1);
				}
			} else {
				Assert.isTrue(false, "unexpected case"); //$NON-NLS-1$
			}
		}
		for (I changedKey : diff.getChangedKeys()) {
			Set<K> elements2 = firstMap.getKeys(changedKey);
			for (K element3 : elements2) {
				changes.add(element3);
				oldValues.put(element3, diff.getOldValue(changedKey));
				V newValue2 = diff.getNewValue(changedKey);
				newValues.put(element3, newValue2);
				wrappedMap.put(element3, newValue2);
