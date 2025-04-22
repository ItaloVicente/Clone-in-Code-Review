    }

    /**
     */
    /* package */ActionSetActionBars getBars() {
        return bars;
    }

    /**
     * Returns the configuration element.
     *
     * @return the configuration element
     */
    public IConfigurationElement getConfigElement() {
        return desc.getConfigurationElement();
    }

    /**
     * Returns the underlying descriptor.
     *
     * @return the descriptor
     */
    public ActionSetDescriptor getDesc() {
        return desc;
    }

    /**
     * Initializes this action set, which is expected to add it actions as required
     * to the given workbench window and action bars.
     *
     * @param window the workbench window
     * @param bars the action bars
     */
    @Override
