		super.createControl(parent);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IReadmeConstants.CONTENT_OUTLINE_PAGE_CONTEXT);

		TreeViewer viewer = getTreeViewer();
		viewer.setContentProvider(new WorkbenchContentProvider());
		viewer.setLabelProvider(new WorkbenchLabelProvider());
		viewer.setInput(getContentOutline(input));
		initDragAndDrop();

		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS + "-end")); //$NON-NLS-1$

		Menu menu = menuMgr.createContextMenu(viewer.getTree());
		viewer.getTree().setMenu(menu);
		getSite().registerContextMenu("org.eclipse.ui.examples.readmetool.outline", menuMgr, viewer); //$NON-NLS-1$

		getSite().getActionBars().setGlobalActionHandler(IReadmeConstants.RETARGET2,
				new OutlineAction(MessageUtil.getString("Outline_Action2"))); //$NON-NLS-1$

		OutlineAction action = new OutlineAction(MessageUtil.getString("Outline_Action3")); //$NON-NLS-1$
		action.setToolTipText(MessageUtil.getString("Readme_Outline_Action3")); //$NON-NLS-1$
		getSite().getActionBars().setGlobalActionHandler(IReadmeConstants.LABELRETARGET3, action);
		action = new OutlineAction(MessageUtil.getString("Outline_Action4")); //$NON-NLS-1$
		getSite().getActionBars().setGlobalActionHandler(IReadmeConstants.ACTION_SET_RETARGET4, action);
		action = new OutlineAction(MessageUtil.getString("Outline_Action5")); //$NON-NLS-1$
		action.setToolTipText(MessageUtil.getString("Readme_Outline_Action5")); //$NON-NLS-1$
		getSite().getActionBars().setGlobalActionHandler(IReadmeConstants.ACTION_SET_LABELRETARGET5, action);
	}

	private IAdaptable getContentOutline(IAdaptable input) {
		return ReadmeModelFactory.getInstance().getContentOutline(input);
	}

	private void initDragAndDrop() {
		int ops = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transfers = new Transfer[] { TextTransfer.getInstance(), PluginTransfer.getInstance() };
		getTreeViewer().addDragSupport(ops, transfers, new ReadmeContentOutlineDragListener(this));
	}

	public void update() {
		getControl().setRedraw(false);
		getTreeViewer().setInput(getContentOutline(input));
		getTreeViewer().expandAll();
		getControl().setRedraw(true);
	}
