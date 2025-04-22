	public static final Comparator<IResource> RESOURCE_NAME_COMPARATOR = new Comparator<IResource>() {
		@Override
		public int compare(IResource r1, IResource r2) {
			return Policy.getComparator().compare(r1.getName(), r2.getName());
		}
	};
