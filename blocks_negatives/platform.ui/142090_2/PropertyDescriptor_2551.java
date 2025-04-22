        return null;
    }

    /**
     * Returns <code>true</code> if this property descriptor is to be always
     * considered incompatible with any other property descriptor.
     * This prevents a property from displaying during multiple
     * selection.
     *
     * @return <code>true</code> to indicate always incompatible
     */
    protected boolean getAlwaysIncompatible() {
        return incompatible;
    }

    /**
     * The <code>PropertyDescriptor</code> implementation of this
     * <code>IPropertyDescriptor</code> method returns the value set by
     * the <code>setCategory</code> method. If unset, this method returns
     * <code>null</code> indicating the default category.
     *
     * @see #setCategory
     */
    @Override
