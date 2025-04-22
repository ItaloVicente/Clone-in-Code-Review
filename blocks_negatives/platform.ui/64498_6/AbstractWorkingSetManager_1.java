		SortedSet visibleSubset = new TreeSet(WorkingSetComparator
				.getInstance());
    		for (Iterator i = workingSets.iterator(); i.hasNext();) {
				IWorkingSet workingSet = (IWorkingSet) i.next();
				if (workingSet.isVisible()) {
					visibleSubset.add(workingSet);
				}
