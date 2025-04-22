        JFacePreferences.getPreferenceStore().addPropertyChangeListener(
                this.colorListener);

    }

    /**
     * Creates the wizard's title area.
     *
     * @param parent the SWT parent for the title area composite
     * @return the created title area composite
     */
    private Composite createTitleArea(Composite parent) {
        Display display = parent.getDisplay();
        Color background = JFaceColors.getBannerBackground(display);
        Color foreground = JFaceColors.getBannerForeground(display);

        Composite titleArea = new Composite(parent, SWT.NONE | SWT.NO_FOCUS);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.verticalSpacing = 0;
        layout.horizontalSpacing = 0;
        layout.numColumns = 2;
        titleArea.setLayout(layout);
        titleArea.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        titleArea.setBackground(background);

        final CLabel messageLabel = new CLabel(titleArea, SWT.LEFT) {
            @Override
