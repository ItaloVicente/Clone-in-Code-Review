    /**
     * Learn whether <code>name</code> is a valid child name for this PropertyOwnerPointer.
     * @param name the QName to test
     * @return <code>true</code> if <code>QName</code> is a valid property name.
     * @since JXPath 1.3
     */
    public boolean isValidProperty(QName name) {
        return isDefaultNamespace(name.getPrefix());
    }

    /**
     * Throws an exception if you try to change the root element, otherwise
     * forwards the call to the parent pointer.
     * @param value to set
     */
    @Override
