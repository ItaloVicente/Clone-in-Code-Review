        return new Object[0];
    }

    /**
     * Returns the coop
     */
    private Boolean getCoop() {
        if (coop == null)
            coop = Boolean.FALSE;
        return coop;
    }

    /**
     * Returns the descriptors
     */
    static  ArrayList<PropertyDescriptor> getDescriptors() {
        return descriptors;
    }

    /* (non-Javadoc)
     * Method declared on IPropertySource
     */
    @Override
