        return combo;
    }

    /**
     * Creates a combo box as a part of the form.
     *
     * @param parent
     *            the combo box parent.
     * @return the combo box.
     */
    public CCombo createCCombo(Composite parent) {
        return createCCombo(parent, SWT.FLAT | SWT.READ_ONLY);
    }

    /**
     * Creates a group as a part of the form.
     *
     * @param parent
     *            the group parent.
     * @param text
     *            the group title.
     * @return the composite.
     */
    public Group createGroup(Composite parent, String text) {
        Group group = new Group(parent, SWT.SHADOW_NONE);
        group.setText(text);
        group.setBackground(getColors().getBackground());
        group.setForeground(getColors().getForeground());
        return group;
    }

    /**
     * Creates a flat form composite as a part of the form.
     *
     * @param parent
     *            the composite parent.
     * @return the composite.
     */
    public Composite createFlatFormComposite(Composite parent) {
        Composite composite = createComposite(parent);
        FormLayout layout = new FormLayout();
        layout.marginWidth = ITabbedPropertyConstants.HSPACE + 2;
        layout.marginHeight = ITabbedPropertyConstants.VSPACE;
        layout.spacing = ITabbedPropertyConstants.VMARGIN + 1;
        composite.setLayout(layout);
        return composite;
    }

    /**
     * Creates a label as a part of the form.
     *
     * @param parent
     *            the label parent.
     * @param text
     *            the label text.
     * @return the label.
     */
    public CLabel createCLabel(Composite parent, String text) {
        return createCLabel(parent, text, SWT.NONE);
    }

    /**
     * Creates a label as a part of the form.
     *
     * @param parent
     *            the label parent.
     * @param text
     *            the label text.
     * @param style
     *            the label style.
     * @return the label.
     */
    public CLabel createCLabel(Composite parent, String text, int style) {
        final CLabel label = new CLabel(parent, style);
        label.setBackground(parent.getBackground());
        label.setText(text);
        return label;
    }

    @Override
