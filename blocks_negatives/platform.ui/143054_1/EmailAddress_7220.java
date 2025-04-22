        return getDescriptors().toArray(
                new IPropertyDescriptor[getDescriptors().size()]);
    }

    /**
     * The <code>EmailAddress</code> implementation of this
     * <code>IPropertySource</code> method returns the following properties
     *
     * 	1) P_USERID returns String, values before "@"
     *	2) P_DOMAIN returns String, values after "@"
     *
     * Observe the available properties must always equal those listed
     * in the property descriptors
     */
    @Override
