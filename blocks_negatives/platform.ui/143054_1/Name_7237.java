        if (key.equals(P_ID_FIRSTNAME))
            return getFirstName() != FIRSTNAME_DEFAULT;
        if (key.equals(P_ID_LASTNAME))
            return getLastName() != LASTNAME_DEFAULT;
        if (key.equals(P_ID_MIDDLENAME))
            return getInitial() != MIDDLENAME_DEFAULT;
        return false;
    }

    /**
     * Implemented as part of IPropertySource framework. Sets the specified property
     * to its default value.
     *
     * @see 	IPropertySource#resetPropertyValue(Object)
     * @param 	property 	The property to reset.
     */
    @Override
