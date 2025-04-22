	private SortedSet<AbstractWorkingSet> workingSets = new TreeSet<>(new Comparator<AbstractWorkingSet>() {
		@Override
		public int compare(AbstractWorkingSet o1, AbstractWorkingSet o2) {
			return o1.getUniqueId().compareTo(o2.getUniqueId());
		}
	});
