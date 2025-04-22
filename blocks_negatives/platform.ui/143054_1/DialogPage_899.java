        return null;
    }

    /**
     * Initializes the computation of horizontal and vertical dialog units based
     * on the size of current font.
     * <p>
     * This method must be called before any of the dialog unit based conversion
     * methods are called.
     * </p>
     *
     * @param testControl
     *            a control from which to obtain the current font
     */
    protected void initializeDialogUnits(Control testControl) {
        GC gc = new GC(testControl);
        gc.setFont(JFaceResources.getDialogFont());
        fontMetrics = gc.getFontMetrics();
        gc.dispose();
    }

    /**
     * Sets the <code>GridData</code> on the specified button to be one that
     * is spaced for the current dialog page units. The method
     * <code>initializeDialogUnits</code> must be called once before calling
     * this method for the first time.
     *
     * @param button
     *            the button to set the <code>GridData</code>
     * @return the <code>GridData</code> set on the specified button
     */
    protected GridData setButtonLayoutData(Button button) {
        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
        Point minSize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
        data.widthHint = Math.max(widthHint, minSize.x);
        button.setLayoutData(data);
        return data;
    }

    /**
     * Tests whether this page's UI content has already been created.
     *
     * @return <code>true</code> if the control has been created, and
     *         <code>false</code> if not
     */
    protected boolean isControlCreated() {
        return control != null;
    }

    /**
     * This default implementation of an <code>IDialogPage</code> method does
     * nothing. Subclasses should override to take some action in response to a
     * help request.
     */
    @Override
