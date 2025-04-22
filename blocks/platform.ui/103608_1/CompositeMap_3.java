			if (added) {
				pendingAdds.add(newValue2);
				rangeSetAdditions.add(newValue2);
			}
			if (added || removed) {
				pendingChanges.put(oldValue1, newValue2);
				pendingChanges.put(newValue2, oldValue1);
			} else {
				changes.add(changedKey);
				oldValues.put(changedKey, secondMap.get(oldValue1));
				wrappedMap.put(changedKey, secondMap.get(newValue2));
