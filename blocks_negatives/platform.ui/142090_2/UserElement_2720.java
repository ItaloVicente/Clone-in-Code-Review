        propertyDescriptor.setCategory(P_PERSONELINFO);
        descriptors.add(propertyDescriptor);

        propertyDescriptor = new ColorPropertyDescriptor(P_ID_HAIRCOLOR,
                P_HAIRCOLOR);
        propertyDescriptor.setCategory(P_PERSONALINFO);
        propertyDescriptor.setHelpContextIds(HAIR_COLOR__CONTEXT);
        descriptors.add(propertyDescriptor);

        propertyDescriptor = new ColorPropertyDescriptor(P_ID_EYECOLOR,
                P_EYECOLOR);
        propertyDescriptor.setCategory(P_PERSONALINFO);
        propertyDescriptor.setHelpContextIds(EYE_COLOR_CONTEXT);
        descriptors.add(propertyDescriptor);

        ArrayList<PropertyDescriptor> parentDescriptors = OrganizationElement.getDescriptors();
        for (int i = 0; i < parentDescriptors.size(); i++) {
            descriptors.add(parentDescriptors.get(i));
        }
    }

    /**
     * Constructor. Default visibility only called from GroupElement.createSubGroup()
     * Creates a new UserElement within the passed parent GroupElement,
     * *
     * @param name the name
     * @param parent the parent
     */
    UserElement(String name, GroupElement parent) {
        super(name, parent);
    }

    /**
     * Returns the address
     */
    private Address getAddress() {
        if (address == null)
            address = new Address();
        return address;
    }

    /**
     * Returns the birthday
     */
    private Birthday getBirthday() {
        if (birthday == null)
            birthday = new Birthday();
        return birthday;
    }

    /* (non-Javadoc)
     * Method declared on IWorkbenchAdapter
     */
    @Override
