        ArrayList standardSets = new ArrayList();
        ArrayList aggregateSets = new ArrayList();
        while (iterator.hasNext()) {
        		IWorkingSet set = (IWorkingSet) iterator.next();
        		if (set instanceof AggregateWorkingSet) {
					aggregateSets.add(set);
				} else {
					standardSets.add(set);
				}
        }
