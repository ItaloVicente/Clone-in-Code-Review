	@Test
	public void testOtherProjectWorkingSet() throws Exception {

		WorkingSetActionProvider provider = (WorkingSetActionProvider) TestAccessHelper
				.getActionProvider(_contentService, _actionService, WorkingSetActionProvider.class);
		IExtensionStateModel extensionStateModel = _contentService
				.findStateModel(WorkingSetsContentProvider.EXTENSION_ID);
		extensionStateModel.setBooleanProperty(WorkingSetsContentProvider.SHOW_TOP_LEVEL_WORKING_SETS, true);

		IWorkingSet workingSet = new WorkingSet("ws1", "ws1", new IAdaptable[] { _p1 });
		IPropertyChangeListener l = provider.getFilterChangeListener();
		PropertyChangeEvent event = new PropertyChangeEvent(this, WorkingSetFilterActionGroup.CHANGE_WORKING_SET, null,
				workingSet);
		l.propertyChange(event);
		_viewer.expandAll();

		TreeItem[] items = _viewer.getTree().getItems();
		assertEquals("Missing working set or 'other projects'", 2, items.length);

		assertTrue("First item needs to be working set", items[0].getData().equals(workingSet));
		assertEquals(workingSet, items[0].getData());
		assertEquals(_p1, items[0].getItem(0).getData());

		assertTrue("Last item needs to be 'other project'",
				items[1].getData().equals(WorkingSetsContentProvider.OTHERS_WORKING_SET));
		assertEquals(_p1.getWorkspace().getRoot().getProjects().length - 1, items[1].getItemCount());

		workingSet.setElements(_p1.getWorkspace().getRoot().getProjects());
		l = provider.getFilterChangeListener();
		event = new PropertyChangeEvent(this, WorkingSetFilterActionGroup.CHANGE_WORKING_SET, null, workingSet);
		l.propertyChange(event);
		_viewer.expandAll();

		items = _viewer.getTree().getItems();
		assertEquals("Should be the single working set", 1, items.length);
		assertEquals(workingSet, items[0].getData());

		workingSet.setElements(new IAdaptable[] { _p1 });
		l = provider.getFilterChangeListener();
		event = new PropertyChangeEvent(this, WorkingSetFilterActionGroup.CHANGE_WORKING_SET, null, workingSet);
		l.propertyChange(event);
		_viewer.expandAll();

		items = _viewer.getTree().getItems();
		assertEquals("Missing working set or 'other projects'", 2, items.length);

		assertTrue("First item needs to be working set", items[0].getData().equals(workingSet));
		assertEquals(workingSet, items[0].getData());
		assertEquals(_p1, items[0].getItem(0).getData());

		assertTrue("Last item needs to be 'other project'",
				items[1].getData().equals(WorkingSetsContentProvider.OTHERS_WORKING_SET));
		assertEquals(_p1.getWorkspace().getRoot().getProjects().length - 1, items[1].getItemCount());

	}

