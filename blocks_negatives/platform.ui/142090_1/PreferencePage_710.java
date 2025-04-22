    protected void contributeButtons(Composite parent) {
    }

    /**
     * Creates and returns the SWT control for the customized body
     * of this preference page under the given parent composite.
     * <p>
     * This framework method must be implemented by concrete subclasses. Any
     * subclass returning a <code>Composite</code> object whose <code>Layout</code>
     * has default margins (for example, a <code>GridLayout</code>) is expected to
     * set the margins of this <code>Layout</code> to 0 pixels.
     * </p>
     *
     * @param parent the parent composite
     * @return the new control
     */
    protected abstract Control createContents(Composite parent);

    /**
     * The <code>PreferencePage</code> implementation of this
     * <code>IDialogPage</code> method creates a description label
     * and button bar for the page. It calls <code>createContents</code>
     * to create the custom contents of the page.
     * <p>
     * If a subclass that overrides this method creates a <code>Composite</code>
     * that has a layout with default margins (for example, a <code>GridLayout</code>)
     * it is expected to set the margins of this <code>Layout</code> to 0 pixels.
     * @see IDialogPage#createControl(Composite)
     */
    @Override
