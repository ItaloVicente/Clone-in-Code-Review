	private static final BitmapWalkHook addAllObjectsHook = new BitmapWalkHook() {
		@Override
		public void preRun(RevWalk walk
				ProgressMonitor pm) {
			((ObjectWalk) walk)
					.setObjectFilter(new BitmapObjectFilter(bitmapResult));
		}
