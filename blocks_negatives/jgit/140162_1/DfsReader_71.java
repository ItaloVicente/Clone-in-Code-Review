	private static final Comparator<FoundObject<?>> FOUND_OBJECT_SORT = new Comparator<FoundObject<?>>() {
		@Override
		public int compare(FoundObject<?> a, FoundObject<?> b) {
			int cmp = a.packIndex - b.packIndex;
			if (cmp == 0)
				cmp = Long.signum(a.offset - b.offset);
			return cmp;
		}
	};
