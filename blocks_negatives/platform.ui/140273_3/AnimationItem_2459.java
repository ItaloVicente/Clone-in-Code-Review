    interface IAnimationContainer {
        /**
         * The animation has started.
         */
        void animationStart();

        /**
         * The animation has ended.
         */
        void animationDone();
    }

    IAnimationContainer animationContainer = new IAnimationContainer() {
        @Override
