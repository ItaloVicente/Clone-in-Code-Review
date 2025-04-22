		for (Iterator it2 = diff.getChangedKeys().iterator(); it2.hasNext();) {
			Object key2 = it2.next();
			Object oldValue2 = diff.getOldValue(key2);
			Object newValue1 = diff.getNewValue(key2);
			if (handleRemoval(oldValue2)) {
				removals.add(oldValue2);
