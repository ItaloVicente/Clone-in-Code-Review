	private Runnable animationStep = new Runnable() {

		@Override
		public void run() {
			if (animationCanceled)
				return;
