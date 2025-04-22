			for (K addedKey : diff.getAddedKeys()) {
				I newValue = diff.getNewValue(addedKey);
				if (!rangeSet.contains(newValue)) {
					pendingAdds.add(newValue);
					rangeSetAdditions.add(newValue);
				} else {
					adds.add(addedKey);
					wrappedMap.put(addedKey, secondMap.get(newValue));
				}
