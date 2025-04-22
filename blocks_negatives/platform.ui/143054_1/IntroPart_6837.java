        return Platform.getAdapterManager().getAdapter(this, adapter);
    }

    /**
     * Returns the configuration element for this part. The configuration
     * element comes from the plug-in registry entry for the extension defining
     * this part.
     *
     * @return the configuration element for this part
     */
    protected IConfigurationElement getConfigurationElement() {
        return configElement;
    }

    /**
     * Returns the default title image.
     *
     * @return the default image
     */
    protected Image getDefaultImage() {
        return PlatformUI.getWorkbench().getSharedImages().getImage(
                ISharedImages.IMG_DEF_VIEW);
    }

    @Override
