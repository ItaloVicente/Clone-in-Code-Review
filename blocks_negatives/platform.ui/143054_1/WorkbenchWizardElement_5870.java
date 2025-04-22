        }
        return Platform.getAdapterManager().getAdapter(this, adapter);
    }

    /**
     * @return IConfigurationElement
     */
    public IConfigurationElement getConfigurationElement() {
        return configurationElement;
    }

    /**
     * Answer the description parameter of this element
     *
     * @return java.lang.String
     */
    @Override
