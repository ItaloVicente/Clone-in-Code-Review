        if (titleImage != null) {
            JFaceResources.getResources().destroyImage(imageDescriptor);
            titleImage = null;
        }

        clearListeners();
    }

    /**
     * Fires a property changed event.
     *
     * @param propertyId
     *            the id of the property that changed
     */
    protected void firePropertyChange(final int propertyId) {
