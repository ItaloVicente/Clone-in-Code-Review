        return 2;
    }

    /**
     * Returns the value that will be used for Scale.setPageIncrement(int).
     *
     * @return the value.
     * @see org.eclipse.swt.widgets.Scale#setPageIncrement(int)
     */
    public int getPageIncrement() {
        return pageIncrementValue;
    }

    /**
     * Returns this field editor's scale control.
     *
     * @return the scale control, or <code>null</code> if no scale field is
     *         created yet
     */
    public Scale getScaleControl() {
        return scale;
    }

    /**
     * Returns this field editor's scale control. The control is created if it
     * does not yet exist.
     *
     * @param parent
     *            the parent
     * @return the scale control
     */
    private Scale getScaleControl(Composite parent) {
        if (scale == null) {
            scale = new Scale(parent, SWT.HORIZONTAL);
            scale.setFont(parent.getFont());
            scale.addSelectionListener(widgetSelectedAdapter(event -> valueChanged()));
            scale.addDisposeListener(event -> scale = null);
        } else {
            checkParent(scale, parent);
        }
        return scale;
    }

    /**
     * Set default values for the various scale fields.  These defaults are:<br>
     * <ul>
     * <li>Minimum  = 0
     * <li>Maximim = 10
     * <li>Increment = 1
     * <li>Page Increment = 1
     * </ul>
     */
    private void setDefaultValues() {
        setValues(0, 10, 1, 1);
    }

    @Override
