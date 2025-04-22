        if (buttonId == detailButtonID) {
            toggleDetailsArea();
        } else {
            super.buttonPressed(buttonId);
        }
    }

    /**
     * Toggles the unfolding of the details area.  This is triggered by
     * the user pressing the details button.
     */
    private void toggleDetailsArea() {
        Point windowSize = getShell().getSize();
        Point oldSize = getContents().computeSize(SWT.DEFAULT, SWT.DEFAULT);

        if (text != null) {
            text.dispose();
            text = null;
            getButton(detailButtonID).setText(
                    IDialogConstants.SHOW_DETAILS_LABEL);
        } else {
            createDropDownText((Composite) getContents());
            getButton(detailButtonID).setText(
                    IDialogConstants.HIDE_DETAILS_LABEL);
        }

        Point newSize = getContents().computeSize(SWT.DEFAULT, SWT.DEFAULT);
        getShell()
                .setSize(
                        new Point(windowSize.x, windowSize.y
                                + (newSize.y - oldSize.y)));
    }

    /**
     * Create this dialog's drop-down list component.
     *
     * @param parent the parent composite
     */
    protected void createDropDownText(Composite parent) {
        text = new Text(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        text.setFont(parent.getFont());

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            detail.printStackTrace(ps);
            ps.flush();
            baos.flush();
            text.setText(baos.toString());
        } catch (IOException e) {
        }

        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
                | GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL
                | GridData.GRAB_VERTICAL);
        data.heightHint = text.getLineHeight() * TEXT_LINE_COUNT;
        data.horizontalSpan = 2;
        text.setLayoutData(data);
    }

    /**
     * Convenience method to open a simple Yes/No question dialog.
     *
     * @param parent the parent shell of the dialog, or <code>null</code> if none
     * @param title the dialog's title, or <code>null</code> if none
     * @param message the message
     * @param detail the error
     * @param defaultIndex the default index of the button to select
     * @return <code>true</code> if the user presses the OK button,
     *    <code>false</code> otherwise
     */
    public static boolean openQuestion(Shell parent, String title,
            String message, Throwable detail, int defaultIndex) {
        String[] labels;
        if (detail == null) {
