        }

        Composite workArea = new Composite(parent, SWT.NONE);
        GridLayout workLayout = new GridLayout();
        workLayout.marginHeight = 0;
        workLayout.marginWidth = 0;
        workLayout.verticalSpacing = 0;
        workLayout.horizontalSpacing = 0;
        workArea.setLayout(workLayout);
        workArea.setLayoutData(new GridData(GridData.FILL_BOTH));

        Color background = JFaceColors.getBannerBackground(parent.getDisplay());
        Color foreground = JFaceColors.getBannerForeground(parent.getDisplay());
        Composite top = (Composite) super.createDialogArea(workArea);

        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.verticalSpacing = 0;
        layout.horizontalSpacing = 0;
        top.setLayout(layout);
        top.setLayoutData(new GridData(GridData.FILL_BOTH));
        top.setBackground(background);
        top.setForeground(foreground);

        final Composite topContainer = new Composite(top, SWT.NONE);
        topContainer.setBackground(background);
        topContainer.setForeground(foreground);

        layout = new GridLayout();
        layout.numColumns = (aboutImage == null || item == null ? 1 : 2);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        layout.verticalSpacing = 0;
        layout.horizontalSpacing = 0;
        topContainer.setLayout(layout);


        GC gc = new GC(parent);
        int topContainerHeightHint = 100;
        try {
