			comparator.saveState(settings);
		}
	}

	public BookmarkNavigator() {
		super();
	}

	void addContributions() {
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();

		openAction = new OpenBookmarkAction(this);
		openAction
				.setImageDescriptor(IDEWorkbenchPlugin.getIDEImageDescriptor("elcl16/gotoobj_tsk.png"));//$NON-NLS-1$

		copyAction = new CopyBookmarkAction(this);
		copyAction.setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));

		pasteAction = new PasteBookmarkAction(this);
		pasteAction.setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));

		removeAction = new RemoveBookmarkAction(this);
		removeAction.setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		removeAction.setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));

		editAction = new EditBookmarkAction(this);

		selectAllAction = new SelectAllAction(this);
		showInNavigatorAction = new ShowInNavigatorAction(getViewSite()
				.getPage(), viewer);

		handleSelectionChanged(StructuredSelection.EMPTY);

		MenuManager mgr = new MenuManager();
		mgr.setRemoveAllWhenShown(true);
		mgr.addMenuListener(mgr1 -> fillContextMenu(mgr1));
		Menu menu = mgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(mgr, viewer);

		IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
		tbm.add(removeAction);
		tbm.add(openAction);
		tbm.update(false);

		IActionBars actionBars = getViewSite().getActionBars();
		actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),
				copyAction);
		actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),
				pasteAction);
		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
				removeAction);
		actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),
				selectAllAction);

		viewer.addOpenListener(event -> openAction.run());
