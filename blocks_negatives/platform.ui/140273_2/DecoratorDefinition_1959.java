        } catch (CoreException exception) {
            handleCoreException(exception);
        }
    }

    /**
     * Return whether or not the decorator registered for element
     * has a label property called property name. If there is an
     * exception disable the receiver and return false.
     * This method should not be called unless a check for
     * isEnabled() has been done first.
     */
    boolean isLabelProperty(Object element, String property) {
            IBaseLabelProvider currentDecorator = internalGetLabelProvider();
            if (currentDecorator != null) {
