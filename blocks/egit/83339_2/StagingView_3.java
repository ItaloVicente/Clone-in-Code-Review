	private TreeViewer createViewer(Composite parent, boolean unstaged,
			final Consumer<IStructuredSelection> dropAction) {
		final TreeViewer viewer = createTree(parent);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(viewer.getControl());
		viewer.getTree().setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TREE_BORDER);
		viewer.setLabelProvider(createLabelProvider(viewer));
		StagingViewContentProvider contentProvider = createStagingContentProvider(
				unstaged);
		viewer.setContentProvider(contentProvider);
		viewer.addDragSupport(DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK,
				new Transfer[] { LocalSelectionTransfer.getTransfer(),
						FileTransfer.getInstance() },
				new StagingDragListener(viewer, contentProvider, unstaged));
		viewer.addDropSupport(DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK,
				new Transfer[] { LocalSelectionTransfer.getTransfer() },
				new DropTargetAdapter() {

					@Override
					public void drop(DropTargetEvent event) {
						event.detail = DND.DROP_COPY;
						if (event.data instanceof IStructuredSelection) {
							final IStructuredSelection selection = (IStructuredSelection) event.data;
							if ((selection instanceof StagingDragSelection)
									&& ((StagingDragSelection) selection)
											.isFromUnstaged() == unstaged) {
								return;
							}
							dropAction.accept(selection);
						}
					}
				});
		viewer.addOpenListener(event -> compareWith(event));
		viewer.setComparator(new StagingEntryComparator(getSortCheckState(),
				getPreferenceStore()
						.getBoolean(UIPreferences.STAGING_VIEW_FILENAME_MODE)));
		enableAutoExpand(viewer);
		addListenerToDisableAutoExpandOnCollapse(viewer);
		return viewer;
	}

