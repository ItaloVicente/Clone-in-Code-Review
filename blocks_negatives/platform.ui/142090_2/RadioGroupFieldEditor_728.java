        return 1;
    }

    /**
     * Returns this field editor's radio group control.
     * @param parent The parent to create the radioBox in
     * @return the radio group control
     */
    public Composite getRadioBoxControl(Composite parent) {
        if (radioBox == null) {

            Font font = parent.getFont();

            if (useGroup) {
                Group group = new Group(parent, SWT.NONE);
                group.setFont(font);
                String text = getLabelText();
                if (text != null) {
