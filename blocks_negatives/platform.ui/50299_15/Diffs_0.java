		final Set addedKeys = new HashSet(newMap.keySet());
		final Set removedKeys = new HashSet();
		final Set changedKeys = new HashSet();
		final Map oldValues = new HashMap();
		final Map newValues = new HashMap();
		for (Iterator it = oldMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry oldEntry = (Entry) it.next();
			Object oldKey = oldEntry.getKey();
