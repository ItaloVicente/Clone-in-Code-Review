			} else {
				Assert.isTrue(false, "unexpected case"); //$NON-NLS-1$
			}
		}
		for (I changedKey : diff.getChangedKeys()) {
			Set<K> elements2 = firstMap.getKeys(changedKey);
			for (Iterator<K> it23 = elements2.iterator(); it23.hasNext();) {
				K element3 = it23.next();
				changes.add(element3);
				oldValues.put(element3, diff.getOldValue(changedKey));
				V newValue2 = diff.getNewValue(changedKey);
				newValues.put(element3, newValue2);
				wrappedMap.put(element3, newValue2);
