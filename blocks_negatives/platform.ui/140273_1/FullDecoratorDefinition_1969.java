        } catch (CoreException exception) {
            handleCoreException(exception);
        }
        return null;
    }

    /**
     * Decorate the text provided for the element type.
     * This method should not be called unless a check for
     * isEnabled() has been done first.
     * Return null if there is no text or if there is an exception.
     */
    String decorateText(String text, Object element) {
        try {
            ILabelDecorator currentDecorator = internalGetDecorator();
            if (currentDecorator != null) {
