     * Returns true only if the control will return a constant height for any
     * width hint larger than the preferred width. Returns false if there is
     * any situation in which the control does not have this property.
     *
     * <p>
     * Note: this method is only important for wrapping controls, and it can
     * safely return false for anything else. AFAIK, all SWT controls have this
     * property, but to be safe they will only be added to the list once the
     * property has been confirmed.
     * </p>
     *
     * @param control
     * @return value as described above
     */
