        return getDescriptors().toArray(
                new IPropertyDescriptor[getDescriptors().size()]);
    }

    /**
     * The <code>Birthday</code> implementation of this
     * <code>IPropertySource</code> method returns the following properties
     *
     * 	1) P_DAY returns java.lang.Integer
     * 	2) P_MONTH returns java.lang.Integer
     *  3) P_YEAR returns java.lang.Integer
     *	4) P_STREET returns java.lang.String
     */
    @Override
