
	public static void setRepo(Repository repo) {
		if (GraphLabelProvider.repo != repo) {
			GraphLabelProvider.repo = repo;
			if (revWalk != null)
				revWalk.release();
			revWalk = new UtilWalk(repo);
		}
	}

	private class NameInfo {
		private String name;

		private int generation;

		private int distance;

		@Override
		public String toString() {
			if (generation == 0) {
				return name;
			} else {
				return String.format("%s~%d", name, generation); //$NON-NLS-1$
			}
		}
	}
