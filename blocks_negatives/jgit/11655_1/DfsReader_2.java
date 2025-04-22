	private static final Comparator<DfsObjectRepresentation> REPRESENTATION_SORT = new Comparator<DfsObjectRepresentation>() {
		public int compare(DfsObjectRepresentation a, DfsObjectRepresentation b) {
			int cmp = a.packIndex - b.packIndex;
			if (cmp == 0)
				cmp = Long.signum(a.offset - b.offset);
			return cmp;
