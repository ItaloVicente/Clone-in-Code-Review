        if (scale != null && !scale.isDisposed()) {
            scale.setFocus();
        }
    }

    /**
     * Set the value to be used for Scale.setIncrement(int) and update the
     * scale.
     *
     * @param increment
     *            a value greater than 0.
     * @see org.eclipse.swt.widgets.Scale#setIncrement(int)
     */
    public void setIncrement(int increment) {
        this.incrementValue = increment;
        updateScale();
    }

    /**
     * Set the value to be used for Scale.setMaximum(int) and update the
     * scale.
     *
     * @param max
     *            a value greater than 0.
     * @see org.eclipse.swt.widgets.Scale#setMaximum(int)
     */
    public void setMaximum(int max) {
        this.maxValue = max;
        updateScale();
    }

    /**
     * Set the value to be used for Scale.setMinumum(int) and update the
     * scale.
     *
     * @param min
     *            a value greater than 0.
     * @see org.eclipse.swt.widgets.Scale#setMinimum(int)
     */
    public void setMinimum(int min) {
        this.minValue = min;
        updateScale();
    }

    /**
     * Set the value to be used for Scale.setPageIncrement(int) and update the
     * scale.
     *
     * @param pageIncrement
     *            a value greater than 0.
     * @see org.eclipse.swt.widgets.Scale#setPageIncrement(int)
     */
    public void setPageIncrement(int pageIncrement) {
        this.pageIncrementValue = pageIncrement;
        updateScale();
    }

    /**
     * Set all Scale values.
     *
     * @param min
     *            the value used for Scale.setMinimum(int).
     * @param max
     *            the value used for Scale.setMaximum(int).
     * @param increment
     *            the value used for Scale.setIncrement(int).
     * @param pageIncrement
     *            the value used for Scale.setPageIncrement(int).
     */
    private void setValues(int min, int max, int increment, int pageIncrement) {
        this.incrementValue = increment;
        this.maxValue = max;
        this.minValue = min;
        this.pageIncrementValue = pageIncrement;
        updateScale();
    }

    /**
     * Update the scale particulars with set values.
     */
    private void updateScale() {
        if (scale != null && !scale.isDisposed()) {
            scale.setMinimum(getMinimum());
            scale.setMaximum(getMaximum());
            scale.setIncrement(getIncrement());
            scale.setPageIncrement(getPageIncrement());
        }
    }

    /**
     * Informs this field editor's listener, if it has one, about a change to
     * the value (<code>VALUE</code> property) provided that the old and new
     * values are different.
     * <p>
     * This hook is <em>not</em> called when the scale is initialized (or
     * reset to the default value) from the preference store.
     * </p>
     */
    protected void valueChanged() {
        setPresentsDefaultValue(false);

        int newValue = scale.getSelection();
        if (newValue != oldValue) {
            fireStateChanged(IS_VALID, false, true);
