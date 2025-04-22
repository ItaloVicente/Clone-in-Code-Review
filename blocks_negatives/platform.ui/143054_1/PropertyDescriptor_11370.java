    }

    /**
     * Returns the input validator for editing the property.
     *
     * @return the validator used to verify correct values for this property,
     *   or <code>null</code>
     */
    protected ICellEditorValidator getValidator() {
        return validator;
    }

    /**
     * Returns whether a label provider has been set on the receiver.
     * @return whether a label provider has been set on the receiver.
     * @see #setLabelProvider
     * @since 3.0
     */
    public boolean isLabelProviderSet() {
        return labelProvider != null;
    }

    /**
     * The <code>SimplePropertyDescriptor</code> implementation of this
     * <code>IPropertyDescriptor</code> method returns true if the other
     * property has the same id and category and <code>getAlwaysIncompatible()</code>
     * returns false
     */
    @Override
