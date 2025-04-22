		}
		for (Iterator it = diff.getChangedKeys().iterator(); it.hasNext();) {
			Object key = it.next();
			Object oldValue = diff.getOldValue(key);
			Object newValue = diff.getNewValue(key);
			if (handleRemoval(oldValue)) {
				removals.add(oldValue);
