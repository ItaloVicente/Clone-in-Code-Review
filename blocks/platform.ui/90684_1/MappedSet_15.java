		domainListener = event -> {
			Set additions = new HashSet();
			for (Iterator it1 = event.diff.getAdditions().iterator(); it1.hasNext();) {
				Object added = it1.next();
				Object mapValue1 = wrappedMap.get(added);
				if (handleAddition(mapValue1)) {
					additions.add(mapValue1);
				}
			}
			Set removals = new HashSet();
			for (Iterator it2 = event.diff.getRemovals().iterator(); it2.hasNext();) {
				Object removed = it2.next();
				Object mapValue2 = wrappedMap.get(removed);
				if (handleRemoval(mapValue2)) {
					removals.add(mapValue2);
				}
			}
			fireSetChange(Diffs.createSetDiff(additions, removals));
		};
