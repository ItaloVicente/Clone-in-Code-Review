		ArrayList<IWorkingSet> standardSets = new ArrayList<>();
		ArrayList<IWorkingSet> aggregateSets = new ArrayList<>();
		while (iterator.hasNext()) {
			IWorkingSet set = iterator.next();
			if (set instanceof AggregateWorkingSet) {
				aggregateSets.add(set);
			} else {
				standardSets.add(set);
			}
		}
