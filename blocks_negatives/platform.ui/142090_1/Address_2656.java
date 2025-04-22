        return getDescriptors().toArray(
                new IPropertyDescriptor[getDescriptors().size()]);
    }

    /**
     * The <code>Address</code> implementation of this
     * <code>IPropertySource</code> method returns the following properties
     *
     * 	1) P_CITY returns java.lang.String
     * 	2) P_POSTALCODE returns java.lang.String
     *  3) P_PROVINCE returns java.lang.String
     *	4) P_STREET returns StreetAddress
     */
    @Override
