        Composite c = createComposite(parent, SWT.NONE);
        return c;
    }

    /**
     * Creates a plain composite as a part of the form.
     *
     * @param parent
     *            the composite parent.
     * @param style
     *            the composite style.
     * @return the composite.
     */
    public Composite createPlainComposite(Composite parent, int style) {
        Composite c = super.createComposite(parent, style);
        c.setBackground(parent.getBackground());
        paintBordersFor(c);
        return c;
    }

    /**
     * Creates a scrolled composite as a part of the form.
     *
     * @param parent
     *            the composite parent.
     * @param style
     *            the composite style.
     * @return the composite.
     */
    public ScrolledComposite createScrolledComposite(Composite parent, int style) {
        ScrolledComposite scrolledComposite = new ScrolledComposite(parent,
            style);
        return scrolledComposite;
    }

    /**
     * Creates a combo box as a part of the form.
     *
     * @param parent
     *            the combo box parent.
     * @param comboStyle
     *            the combo box style.
     * @return the combo box.
     */
    public CCombo createCCombo(Composite parent, int comboStyle) {
        CCombo combo = new CCombo(parent, comboStyle);
        adapt(combo, true, false);
