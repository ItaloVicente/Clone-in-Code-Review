	interface IAnimationContainer {
		public abstract void animationStart();

		public abstract void animationDone();
	}

	IAnimationContainer animationContainer = new IAnimationContainer() {
		@Override
