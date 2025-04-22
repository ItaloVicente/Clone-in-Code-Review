	private static final BitmapWalkHook noDupicatesInWalkHook = new BitmapWalkHook() {
		@Override
		public void run(RevWalk walk
				ProgressMonitor pm) {
			((ObjectWalk) walk)
					.setObjectFilter(new BitmapObjectFilter(bitmapResult));
		}
	};
