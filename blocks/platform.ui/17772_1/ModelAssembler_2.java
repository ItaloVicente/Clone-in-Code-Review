	private static class TopoSortValue implements Comparable<TopoSortValue> {
		private final List<IExtension> extensions = new ArrayList<IExtension>();

		private int relevantRequiresAmount;

		public int compareTo(TopoSortValue o) {
			return relevantRequiresAmount - o.relevantRequiresAmount;
