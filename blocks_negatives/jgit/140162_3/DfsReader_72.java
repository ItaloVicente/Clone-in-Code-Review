	private static final Comparator<DfsObjectToPack> OFFSET_SORT = new Comparator<DfsObjectToPack>() {
		@Override
		public int compare(DfsObjectToPack a, DfsObjectToPack b) {
			return Long.signum(a.getOffset() - b.getOffset());
		}
	};
