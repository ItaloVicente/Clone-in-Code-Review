	public static class HierarchyComparator implements Comparator {

		private IHierarchalThemeElementDefinition[] definitions;

		public HierarchyComparator(IHierarchalThemeElementDefinition[] definitions) {
			this.definitions = definitions;
		}

		@Override
