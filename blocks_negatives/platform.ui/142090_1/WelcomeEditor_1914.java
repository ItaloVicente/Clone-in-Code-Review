        Label titleImage = new Label(titleArea, SWT.LEFT);
        titleImage.setBackground(background);
        titleImage.setImage(PlatformUI.getWorkbench().getSharedImages()
                .getImage(IDEInternalWorkbenchImages.IMG_OBJS_WELCOME_BANNER));
        gd = new GridData();
        gd.horizontalAlignment = GridData.END;
        titleImage.setLayoutData(gd);
