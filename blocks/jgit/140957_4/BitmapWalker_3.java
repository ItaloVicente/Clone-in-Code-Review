	private static final MissingStartHook addAllObjectsHook = new MissingStartHook() {
		@Override
		public void preWalk(RevWalk walk
				ProgressMonitor pm) {
			((ObjectWalk) walk)
					.setObjectFilter(new BitmapObjectFilter(bitmapResult));
		}
