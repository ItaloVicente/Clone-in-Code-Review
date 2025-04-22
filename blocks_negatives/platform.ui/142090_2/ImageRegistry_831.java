    /**
     * Contains the data for an entry in the registry.
     */
    private static class Entry {
    	/** the image */
        protected Image image;

        /** the descriptor */
        protected ImageDescriptor descriptor;
    }

    private static class OriginalImageDescriptor extends ImageDescriptor {
        private Image original;
        private int refCount = 0;
        private Device originalDisplay;

        /**
         * @param original the original image
         * @param originalDisplay the device the image is part of
         */
        public OriginalImageDescriptor(Image original, Device originalDisplay) {
            this.original = original;
            this.originalDisplay = originalDisplay;
        }

        @Override
