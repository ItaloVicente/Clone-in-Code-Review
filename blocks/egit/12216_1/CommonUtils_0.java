	public static final Comparator<IResource> RESOURCE_NAME_COMPARATOR = new Comparator<IResource>() {
		@SuppressWarnings("unchecked")
		private final Comparator<String> stringComparator = Policy
				.getComparator();

		public int compare(IResource r1, IResource r2) {
			return stringComparator.compare(r1.getName(), r2.getName());
		}
	};

