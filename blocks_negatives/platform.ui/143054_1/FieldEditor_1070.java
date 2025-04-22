        propertyChangeListener.propertyChange(new PropertyChangeEvent(this,
                property, oldValue, newValue));
    }

    /**
     * Returns the symbolic font name used by this field editor.
     *
     * @return the symbolic font name
     */
    public String getFieldEditorFontName() {
        return JFaceResources.DIALOG_FONT;
    }

    /**
     * Returns the label control.
     *
     * @return the label control, or <code>null</code>
     *  if no label control has been created
     */
    protected Label getLabelControl() {
        return label;
    }

    /**
     * Returns this field editor's label component.
     * <p>
     * The label is created if it does not already exist
     * </p>
     *
     * @param parent the parent
     * @return the label control
     */
    public Label getLabelControl(Composite parent) {
        if (label == null) {
            label = new Label(parent, SWT.LEFT);
            label.setFont(parent.getFont());
            String text = getLabelText();
            if (text != null) {
