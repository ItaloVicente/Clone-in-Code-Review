	private IMapChangeListener<K, V> changeListener = event -> {
		if (event.diff.getAddedKeys().contains(key)) {
			final V newValue = event.diff.getNewValue(key);
			if (newValue != null) {
				fireValueChange(Diffs.createValueDiff(null, newValue));
			}
		} else if (event.diff.getChangedKeys().contains(key)) {
			fireValueChange(Diffs.createValueDiff(
					event.diff.getOldValue(key),
					event.diff.getNewValue(key)));
		} else if (event.diff.getRemovedKeys().contains(key)) {
			final V oldValue = event.diff.getOldValue(key);
			if (oldValue != null) {
				fireValueChange(Diffs.createValueDiff(oldValue, null));
