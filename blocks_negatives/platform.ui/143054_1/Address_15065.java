        return this.toString();
    }

    /**
     * Returns the postal code
     */
    private String getPostalCode() {
        if (postalCode == null)
            postalCode = POSTALCODE_DEFAULT;
        return postalCode;
    }

    /* (non-Javadoc)
     * Method declared on IPropertySource
     */
    @Override
