        }

        return true;
    }

    /**
     * Sets a flag indicating whether this property descriptor is to be always
     * considered incompatible with any other property descriptor.
     * Setting this flag prevents a property from displaying during multiple
     * selection.
     *
     * @param flag <code>true</code> to indicate always incompatible
     */
    public void setAlwaysIncompatible(boolean flag) {
        incompatible = flag;
    }

    /**
     * Sets the category for this property descriptor.
     *
     * @param category the category for the descriptor, or <code>null</code> if none
     * @see #getCategory
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Sets the description for this property descriptor.
     * The description should be limited to a single line so that it can be
     * displayed in the status line.
     *
     * @param description the description, or <code>null</code> if none
     * @see #getDescription
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the the filter flags for this property descriptor.
     * The description should be limited to a single line so that it can be
     * displayed in the status line.
     * <p>
     * Valid values for these flags are declared as constants on
     *  <code>IPropertySheetEntry</code>
     * </p>
     *
     * @param value the filter flags
     * @see #getFilterFlags
     */
    public void setFilterFlags(String value[]) {
        filterFlags = value;
    }

    /**
     * Sets the help context id for this property descriptor.
     *
     * NOTE: Help support system API's changed since 2.0 and arrays
     * of contexts are no longer supported.
     * </p>
     * <p>
     * Thus the only valid parameter type for this method
     * is a <code>String</code> representing a context id.
     * The previously valid parameter types are deprecated.
     * The plural name for this method is unfortunate.
     * </p>
     *
     * @param contextIds the help context ids, or <code>null</code> if none
     * @see #getHelpContextIds
     */
    public void setHelpContextIds(Object contextIds) {
        helpIds = contextIds;
    }

    /**
     * Sets the label provider for this property descriptor.
     * <p>
     * If no label provider is set an instance of <code>LabelProvider</code>
     * will be created as the default when needed.
     * </p>
     *
     * @param provider the label provider for the descriptor, or
     *   <code>null</code> if the default label provider should be used
     * @see #getLabelProvider
     */
    public void setLabelProvider(ILabelProvider provider) {
        labelProvider = provider;
    }

    /**
     * Sets the input validator for the cell editor for this property descriptor.
     * <p>
     * [Issue: This method should be unnecessary is the cell editor's own
     *  validator is used.
     * ]
     * </p>
     *
     * @param validator the cell input validator, or <code>null</code> if none
     */
    public void setValidator(ICellEditorValidator validator) {
        this.validator = validator;
    }
