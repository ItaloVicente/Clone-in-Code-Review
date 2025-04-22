    /**
     * Create a button for the preference page.
     * @param parent
     * @param label
     */
    private Button createButton(Composite parent, String label) {
        Button button = new Button(parent, SWT.PUSH | SWT.CENTER);
        button.setText(label);
        myApplyDialogFont(button);
        setButtonLayoutData(button);
        button.setEnabled(false);
        return button;
    }
