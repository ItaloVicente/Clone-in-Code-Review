        if (imageDescriptor != null) {
            JFaceResources.getResources().destroyImage(imageDescriptor);
        }

        clearListeners();
        partChangeListeners.clear();
    }

    /**
     * Fires a property changed event.
     *
     * @param propertyId the id of the property that changed
     */
    protected void firePropertyChange(final int propertyId) {
