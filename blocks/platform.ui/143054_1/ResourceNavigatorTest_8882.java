		assertTrue("Second selection wrong size, should be only one.", treeSel2
				.size() == 1);
		IResource resource2 = (IResource) treeSel2.getFirstElement();
		assertTrue("Second selection contains wrong project resource.",
				resource2.equals(p2));
	}

	public void testWorkingSet() throws Throwable {
		setupView();
		setupResources();

		ResourceNavigator navigator = ((ResourceNavigator) view);
		IWorkingSetManager workingSetManager = fWorkbench
				.getWorkingSetManager();
		IWorkingSet workingSet = workingSetManager.createWorkingSet("ws1",
				new IAdaptable[] { f1 });

		assertNull(navigator.getWorkingSet());

		navigator.setWorkingSet(workingSet);
		assertEquals(workingSet, navigator.getWorkingSet());

		navigator.setWorkingSet(null);
		assertNull(navigator.getWorkingSet());

		FileUtil.createFile("f11.txt", p1);
		navigator.setWorkingSet(workingSet);
		TreeViewer viewer = navigator.getTreeViewer();
		viewer.expandAll();
		TreeItem[] items = viewer.getTree().getItems();
		assertEquals(p1, items[0].getData());
		items = items[0].getItems();
		assertEquals(f1, items[0].getData());
	}
