	interface IAnimationContainer {
		void animationStart();

		void animationDone();
	}

	IAnimationContainer animationContainer = new IAnimationContainer() {
		@Override
