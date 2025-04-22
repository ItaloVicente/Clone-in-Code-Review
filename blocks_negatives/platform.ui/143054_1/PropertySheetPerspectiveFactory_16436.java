        String editorArea = layout.getEditorArea();
        IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, (float) 0.33,
                editorArea);
        topLeft.addPlaceholder(IPageLayout.ID_PROP_SHEET);

        IFolderLayout bottomRight = layout.createFolder("bottomRight", IPageLayout.BOTTOM,
                (float) 0.55, editorArea);

        bottomRight.addPlaceholder(SelectionProviderView.ID);

        IFolderLayout topRight = layout.createFolder("topRight", IPageLayout.RIGHT, (float) 0.33,
                editorArea);
        topRight.addPlaceholder(NonRestorableView.ID);
        topRight.addPlaceholder(SaveableMockViewPart.ID);

    }

    public static void applyPerspective(IWorkbenchPage activePage) {
        IPerspectiveDescriptor desc = activePage.getWorkbenchWindow().getWorkbench()
                .getPerspectiveRegistry().findPerspectiveWithId(
                        PropertySheetPerspectiveFactory.class.getName());
        activePage.setPerspective(desc);
        while (Display.getCurrent().readAndDispatch()) {
