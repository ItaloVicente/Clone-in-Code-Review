			Set removals = new HashSet();
			for (Iterator it = event.diff.getRemovals().iterator(); it.hasNext();) {
				Object removed = it.next();
				Object mapValue = wrappedMap.get(removed);
				if (handleRemoval(mapValue)) {
					removals.add(mapValue);
				}
