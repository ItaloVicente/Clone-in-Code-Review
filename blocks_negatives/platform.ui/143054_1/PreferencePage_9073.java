    }

    /**
     * Creates a composite with a highlighted Note entry and a message text.
     * This is designed to take up the full width of the page.
     *
     * @param font the font to use
     * @param composite the parent composite
     * @param title the title of the note
     * @param message the message for the note
     * @return the composite for the note
     */
    protected Composite createNoteComposite(Font font, Composite composite,
            String title, String message) {
        Composite messageComposite = new Composite(composite, SWT.NONE);
        GridLayout messageLayout = new GridLayout();
        messageLayout.numColumns = 2;
        messageLayout.marginWidth = 0;
        messageLayout.marginHeight = 0;
        messageComposite.setLayout(messageLayout);
        messageComposite.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_FILL));
        messageComposite.setFont(font);

        final Label noteLabel = new Label(messageComposite, SWT.BOLD);
        noteLabel.setText(title);
        noteLabel.setFont(JFaceResources.getFontRegistry().getBold(
