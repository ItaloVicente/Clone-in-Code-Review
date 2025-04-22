        if (!this.enabled && decorator != null) {
            IBaseLabelProvider cached = decorator;
            decorator = null;
            disposeCachedDecorator(cached);
        }
    }

    /**
     * Decorate the image provided for the element type.
     * This method should not be called unless a check for
     * isEnabled() has been done first.
     * Return null if there is no image or if an error occurs.
     */
    Image decorateImage(Image image, Object element) {
        try {
            ILabelDecorator currentDecorator = internalGetDecorator();
            if (currentDecorator != null) {
