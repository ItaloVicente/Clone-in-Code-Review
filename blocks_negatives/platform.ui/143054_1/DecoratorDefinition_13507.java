        return RegistryReader.getDescription(definingElement);
    }

    /**
     * Gets the enabled.
     * @return Returns a boolean
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled flag and adds or removes the decorator
     * manager as a listener as appropriate.
     * @param newState The enabled to set
     */
    public void setEnabled(boolean newState) {

        if (this.enabled != newState) {
            this.enabled = newState;
            try {
                refreshDecorator();
            } catch (CoreException exception) {
                handleCoreException(exception);
            }

        }
    }

    /**
     * Refresh the current decorator based on our enable
     * state.
     */
    protected abstract void refreshDecorator() throws CoreException;

    /**
     * Dispose the decorator instance and remove listeners
     * as appropirate.
     * @param disposedDecorator
     */
    protected void disposeCachedDecorator(IBaseLabelProvider disposedDecorator) {
        disposedDecorator.removeListener(WorkbenchPlugin.getDefault()
                .getDecoratorManager());
        disposedDecorator.dispose();

    }

    /**
     * Return whether or not this decorator should be
     * applied to adapted types.
     *
     * @return whether or not this decorator should be
     * applied to adapted types
     */
    public boolean isAdaptable() {
