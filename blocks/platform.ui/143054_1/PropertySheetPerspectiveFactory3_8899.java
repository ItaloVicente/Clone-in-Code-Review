		String editorArea = layout.getEditorArea();

		IFolderLayout bottomRight = layout.createFolder(
				"bottomRight", IPageLayout.BOTTOM, (float) 0.55,
				editorArea);


		bottomRight.addPlaceholder(IPageLayout.ID_PROP_SHEET);
		bottomRight.addPlaceholder(NonRestorableView.ID);
		bottomRight.addPlaceholder(SaveableMockViewPart.ID);
		bottomRight.addPlaceholder(IPageLayout.ID_PROJECT_EXPLORER);
		bottomRight.addPlaceholder(IPageLayout.ID_RES_NAV);

		IFolderLayout topLeft = layout.createFolder(
				"topLeft", IPageLayout.LEFT, (float) 0.33,
				editorArea);
		topLeft.addPlaceholder(SelectionProviderView.ID);
	}

	public static void applyPerspective(IWorkbenchPage activePage){
		IPerspectiveDescriptor desc = activePage.getWorkbenchWindow().getWorkbench()
			.getPerspectiveRegistry().findPerspectiveWithId(PropertySheetPerspectiveFactory3.class.getName());
		activePage.setPerspective(desc);
		while (Display.getCurrent().readAndDispatch()) {
