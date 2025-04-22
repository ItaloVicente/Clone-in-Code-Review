	public void testPropertiesView2() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		IEditorPart part = openEditor(window, MTEST01_FILE);

		window.getActivePage().activate(part);
		MultiPageResourceEditor editor = (MultiPageResourceEditor) part;
		editor.updateSelection();

		PropertySheet propertiewView = (PropertySheet) window.getActivePage().showView(IPageLayout.ID_PROP_SHEET);
		processUiEvents();

		Tree tree = (Tree) propertiewView.getCurrentPage().getControl();

		assertFalse(tree.getItemCount() == 0);
	}

