	private SortedSet workingSets = new TreeSet(new Comparator() {
		@Override
		public int compare(Object o1, Object o2) {
			return ((AbstractWorkingSet) o1).getUniqueId().compareTo(
					((AbstractWorkingSet) o2).getUniqueId());
		}
	});
