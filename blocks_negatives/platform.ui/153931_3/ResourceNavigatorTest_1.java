	 * This test should be moved to an interactive test suite as this
	 * test causes a dialog to popup when the resource is deleted by
	 * the delete action
	 *
	 public void testGlobalDeleteAction() throws Throwable {
	 setupView();
	 setupResources();

	 IStructuredSelection sel = new StructuredSelection(f1);
	 ((ResourceNavigator) view).selectReveal(sel);

	 ActionUtil.runActionUsingPath(this, workbenchWindow, IWorkbenchActionConstants.M_EDIT + '/' + IWorkbenchActionConstants.DELETE);

	 assertTrue("Selected file was not deleted via Edit->Delete action.", p1.findMember(f1.getName()) == null);
	 f1 = null;
	 }
