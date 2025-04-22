    }

    /**
     * Sets the image descriptor
     */
    void setImageDescriptor(ImageDescriptor desc) {
        imageDescriptor = desc;
    }

    /**
     * Sets the name
     */
    void setName(String newName) {
        name = newName;
    }

    /**
     * Sets this instance's parent back pointer.
     */
    void setParent(GroupElement newParent) {
        parent = newParent;
    }

    /**
     * The <code>OrganizationElement</code> implementation of this
     * <code>IPropertySource</code> method returns the following properties
     * defines the following Setable properties
     *
     *	1) P_NAME, expects String, sets the name of this OrganizationElement
     */
    @Override
