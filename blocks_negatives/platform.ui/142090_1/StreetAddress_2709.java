        return getDescriptors().toArray(
                new IPropertyDescriptor[getDescriptors().size()]);
    }

    /**
     * The <code>Name</code> implementation of this
     * <code>IPropertySource</code> method returns the following properties
     *
     * 	1) P_BUILD_NO returns java.lang.Integer
     * 	2) P_APTBOX returns java.lang.String
     *	3) P_STREET returns java.lang.String
     */
    @Override
