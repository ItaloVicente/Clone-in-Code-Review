    }

    /**
     * Add a listener for the decorator.If there is an exception
     * then inform the user and disable the receiver.
     * This method should not be called unless a check for
     * isEnabled() has been done first.
     */
    void addListener(ILabelProviderListener listener) {
        try {
            IBaseLabelProvider currentDecorator = internalGetLabelProvider();
            if (currentDecorator != null) {
