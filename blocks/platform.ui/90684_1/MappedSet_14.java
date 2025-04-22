		}
		for (Iterator it3 = diff.getAddedKeys().iterator(); it3.hasNext();) {
			Object key3 = it3.next();
			Object newValue2 = diff.getNewValue(key3);
			if (handleAddition(newValue2)) {
				additions.add(newValue2);
