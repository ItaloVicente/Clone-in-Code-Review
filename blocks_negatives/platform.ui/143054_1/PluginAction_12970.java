        selectionChanged(sel);
    }

    /**
     * For testing purposes only.
     *
     * @return the selection
     * @since 3.1
     */
    public ISelection getSelection() {
    	return selection;
    }

    /**
     * Returns the action identifier this action overrides.
     * Default implementation returns <code>null</code>.
     *
     * @return the action identifier to override or <code>null</code>
     */
    public String getOverrideActionId() {
        return null;
    }

    /**
     * @return the IConfigurationElement used to create this PluginAction.
     *
     * @since 3.0
     */
    protected IConfigurationElement getConfigElement() {
        return configElement;
    }

    @Override
