        getPreferenceStore()
                .setValue(getPreferenceName(), scale.getSelection());
    }

    /**
     * Returns the value that will be used for Scale.setIncrement(int).
     *
     * @return the value.
     * @see org.eclipse.swt.widgets.Scale#setIncrement(int)
     */
    public int getIncrement() {
        return incrementValue;
    }

    /**
     * Returns the value that will be used for Scale.setMaximum(int).
     *
     * @return the value.
     * @see org.eclipse.swt.widgets.Scale#setMaximum(int)
     */
    public int getMaximum() {
        return maxValue;
    }

    /**
     * Returns the value that will be used for Scale.setMinimum(int).
     *
     * @return the value.
     * @see org.eclipse.swt.widgets.Scale#setMinimum(int)
     */
    public int getMinimum() {
        return minValue;
    }

    @Override
