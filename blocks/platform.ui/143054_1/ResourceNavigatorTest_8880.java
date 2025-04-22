	}

	public void fixTestGlobalBookmarkAction() throws Throwable {
		setupView();
		setupResources();

		IStructuredSelection sel = new StructuredSelection(f1);
		((ResourceNavigator) view).selectReveal(sel);

		int oldCount = (f1.findMarkers(IMarker.BOOKMARK, true,
				IResource.DEPTH_INFINITE)).length;

		ActionUtil.runActionUsingPath(this, workbenchWindow,
				IWorkbenchActionConstants.M_EDIT + '/'
						+ IWorkbenchActionConstants.BOOKMARK);

		int newCount = (f1.findMarkers(IMarker.BOOKMARK, true,
				IResource.DEPTH_INFINITE)).length;
		assertTrue(
				"Selected file was not bookmarked via Edit->Bookmark action.",
				oldCount + 1 == newCount);
	}

	 public void testGlobalDeleteAction() throws Throwable {
	 setupView();
	 setupResources();

	 IStructuredSelection sel = new StructuredSelection(f1);
	 ((ResourceNavigator) view).selectReveal(sel);

	 ActionUtil.runActionUsingPath(this, workbenchWindow, IWorkbenchActionConstants.M_EDIT + '/' + IWorkbenchActionConstants.DELETE);

	 assertTrue("Selected file was not deleted via Edit->Delete action.", p1.findMember(f1.getName()) == null);
	 f1 = null;
	 }

	public void testSelectReveal() throws Throwable {
		setupView();
		setupResources();

		ISetSelectionTarget part = (ISetSelectionTarget) view;
		TreeViewer tree = ((ResourceNavigator) view).getViewer();

		IStructuredSelection sel1 = new StructuredSelection(f1);
		part.selectReveal(sel1);
