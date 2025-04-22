    interface IAnimationContainer {
        /**
         * The animation has started.
         */
        public abstract void animationStart();

        /**
         * The animation has ended.
         */
        public abstract void animationDone();
    }

    IAnimationContainer animationContainer = new IAnimationContainer() {
        @Override
