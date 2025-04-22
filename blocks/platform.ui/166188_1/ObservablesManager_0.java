
	private static class ManagerEntry {
		public final boolean disposeContext;
		public final boolean disposeTargets;
		public final boolean disposeModels;

		public ManagerEntry(boolean disposeContext, boolean disposeTargets, boolean disposeModels) {
			this.disposeContext = disposeContext;
			this.disposeTargets = disposeTargets;
			this.disposeModels = disposeModels;
		}
	}
