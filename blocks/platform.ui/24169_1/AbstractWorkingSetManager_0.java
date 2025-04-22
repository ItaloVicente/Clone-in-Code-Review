	private SortedSet<IWorkingSet> workingSets = new TreeSet<IWorkingSet>(
			new Comparator<IWorkingSet>() {
				@Override
				public int compare(IWorkingSet o1, IWorkingSet o2) {
					return ((AbstractWorkingSet) o1).getUniqueId().compareTo(((AbstractWorkingSet) o2).getUniqueId());
				}
