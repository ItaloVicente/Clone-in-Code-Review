	}

	private static class TopoSortValue implements Comparable<TopoSortValue> {
		private final List<IExtension> extensions = new ArrayList<IExtension>();

		private final Set<String> requiredBundles = new HashSet<String>();

		private final String symbolicNameOfProvider;

		public TopoSortValue(String symbolicNameOfProvider) {
			if (symbolicNameOfProvider == null)
				throw new IllegalArgumentException("No symbolic name of providing bundle given!"); //$NON-NLS-1$

			this.symbolicNameOfProvider = symbolicNameOfProvider;
		}

		public int compareTo(TopoSortValue o) {
			return o.requiredBundles.size() - requiredBundles.size();
		}

		public String getSymbolicNameOfProvider() {
			return symbolicNameOfProvider;
		}

		public boolean hasUnresolvedDependencies() {
			return !requiredBundles.isEmpty();
		}

		public void resolvedDependency(String symbolicNameOfDependency) {
			requiredBundles.remove(symbolicNameOfDependency);
		}

		public void addDependencies(Set<String> dependencies) {
			requiredBundles.addAll(dependencies);
		}

		public List<IExtension> getExtensions() {
			return extensions;
		}

		public String requiredBundlesToStirng() {
			return requiredBundles.toString();
		}

		@Override
		public String toString() {
			return "TSV - " + symbolicNameOfProvider + " --> " + requiredBundles + " (extensions: " + extensions.size() + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
