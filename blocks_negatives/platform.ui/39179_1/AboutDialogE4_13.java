        Composite featureContainer = new Composite(parent, SWT.NONE);
        RowLayout rowLayout = new RowLayout();
        rowLayout.wrap = true;
        featureContainer.setLayout(rowLayout);
        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        featureContainer.setLayoutData(data);

        for (int i = 0; i < bundleGroupInfos.length; i++) {
