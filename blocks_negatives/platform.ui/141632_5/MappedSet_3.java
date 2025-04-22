			for (Iterator it = diff.getAddedKeys().iterator(); it.hasNext();) {
				Object key = it.next();
				Object newValue = diff.getNewValue(key);
				if (handleAddition(newValue)) {
					additions.add(newValue);
				}
