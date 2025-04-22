        /**
         * Hook the listeners onto the various registries.
         */
        public void hookListeners() {
            colorRegistry.addListener(listener);
            fontRegistry.addListener(listener);
        }
