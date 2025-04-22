		Map oldValues = new HashMap();
		Map newValues = new HashMap();
		Set changedKeys = new HashSet();
		Set addedKeys = new HashSet();
		for (Iterator it = m.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			Object newValue = entry.getValue();
