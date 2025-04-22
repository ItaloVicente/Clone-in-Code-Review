		clipboard = new Clipboard(parent.getDisplay());
		createTable(parent);
		viewer = new TableViewer(table);
		createColumns();

		comparator = new BookmarkSorter();
		viewer.setContentProvider(new BookmarkContentProvider(this));
		viewer.setLabelProvider(new BookmarkLabelProvider(this));
		viewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
		viewer.setComparator(comparator);

		IDialogSettings workbenchSettings = getPlugin().getDialogSettings();
		IDialogSettings settings = workbenchSettings
				.getSection("BookmarkSortState");//$NON-NLS-1$
		comparator.restoreState(settings);

		addContributions();
		initDragAndDrop();
		createSortActions();
		fillActionBars();
		updateSortState();
		updatePasteEnablement();

		getSite().setSelectionProvider(viewer);

		if (memento != null) {
