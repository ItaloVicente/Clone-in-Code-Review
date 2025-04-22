	private ISetChangeListener domainListener;

	private IMapChangeListener mapChangeListener = event -> {
		MapDiff diff = event.diff;
		Set additions = new HashSet();
		Set removals = new HashSet();
		for (Iterator it1 = diff.getRemovedKeys().iterator(); it1.hasNext();) {
			Object key1 = it1.next();
			Object oldValue1 = diff.getOldValue(key1);
			if (handleRemoval(oldValue1)) {
				removals.add(oldValue1);
