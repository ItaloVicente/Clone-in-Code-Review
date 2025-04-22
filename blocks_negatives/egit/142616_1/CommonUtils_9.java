	public static final Comparator<Ref> REF_ASCENDING_COMPARATOR = new Comparator<Ref>() {
		@Override
		public int compare(Ref o1, Ref o2) {
			return STRING_ASCENDING_COMPARATOR.compare(o1.getName(), o2.getName());
		}
	};
