	private IMapChangeListener mapChangeListener = (IMapChangeListener) (MapChangeEvent event) -> {
	    MapDiff diff = event.diff;
	    Set additions = new HashSet();
	    Set removals = new HashSet();
	    for (Iterator it = diff.getRemovedKeys().iterator(); it.hasNext();) {
		Object key = it.next();
		Object oldValue = diff.getOldValue(key);
		if (handleRemoval(oldValue)) {
		    removals.add(oldValue);
		}
	    }
	    for (Iterator it = diff.getChangedKeys().iterator(); it.hasNext();) {
		Object key = it.next();
		Object oldValue = diff.getOldValue(key);
		Object newValue = diff.getNewValue(key);
		if (handleRemoval(oldValue)) {
		    removals.add(oldValue);
		}
		if (handleAddition(newValue)) {
		    additions.add(newValue);
		}
	    }
	    for (Iterator it = diff.getAddedKeys().iterator(); it.hasNext();) {
		Object key = it.next();
		Object newValue = diff.getNewValue(key);
		if (handleAddition(newValue)) {
		    additions.add(newValue);
