        return previewer.getControl();
    }

    /**
     * Returns the value control for this field editor. The value control
     * displays the currently selected font name.
     * @param parent The Composite to create the viewer in if required
     * @return the value control
     */
    protected Label getValueControl(Composite parent) {
        if (valueControl == null) {
            valueControl = new Label(parent, SWT.LEFT);
            valueControl.setFont(parent.getFont());
            valueControl.addDisposeListener(event -> valueControl = null);
        } else {
            checkParent(valueControl, parent);
        }
        return valueControl;
    }

    /**
     * Sets the text of the change button.
     *
     * @param text the new text
     */
    public void setChangeButtonText(String text) {
        Assert.isNotNull(text);
        changeButtonText = text;
        if (changeFontButton != null) {
