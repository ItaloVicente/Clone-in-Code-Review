	private final Comparator<IConfigurationElement> comparer = new Comparator<IConfigurationElement>() {
		@Override
		public int compare(IConfigurationElement c1, IConfigurationElement c2) {
			return c1.getContributor().getName().compareToIgnoreCase(c2.getContributor().getName());
		}
	};
