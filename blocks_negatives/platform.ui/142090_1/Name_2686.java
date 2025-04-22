        return getDescriptors().toArray(
                new IPropertyDescriptor[getDescriptors().size()]);
    }

    /**
     * The <code>Name</code> implementation of this
     * <code>IPropertySource</code> method returns the following properties
     *
     * 	1) P_FIRSTNAME returns String, firstname
     * 	2) P_LASTNAME returns String, lastname
     *  3) P_MIDDLENAME returns String, middle
     */
    @Override
