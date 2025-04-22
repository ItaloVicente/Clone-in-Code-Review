		unstagedViewer = createTree(unstagedComposite);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(unstagedViewer.getControl());
		unstagedViewer.getTree().setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TREE_BORDER);
		unstagedViewer.setLabelProvider(createLabelProvider(unstagedViewer));
		unstagedViewer.setContentProvider(createStagingContentProvider(true));
		unstagedViewer.addDragSupport(DND.DROP_MOVE | DND.DROP_COPY
				| DND.DROP_LINK,
				new Transfer[] { LocalSelectionTransfer.getTransfer(),
						FileTransfer.getInstance() }, new StagingDragListener(
						unstagedViewer));
		unstagedViewer.addDropSupport(DND.DROP_MOVE,
				new Transfer[] { LocalSelectionTransfer.getTransfer() },
				new DropTargetAdapter() {
					@Override
					public void drop(DropTargetEvent event) {
						event.detail = DND.DROP_COPY;
						if (event.data instanceof IStructuredSelection) {
							final IStructuredSelection selection = (IStructuredSelection) event.data;
							unstage(selection);
						}
					}

					@Override
					public void dragOver(DropTargetEvent event) {
						event.detail = DND.DROP_MOVE;
					}
				});
		unstagedViewer.addOpenListener(new IOpenListener() {
			@Override
			public void open(OpenEvent event) {
				compareWith(event);
			}
		});
		unstagedViewer.setComparator(
				new StagingEntryComparator(getSortCheckState(), getPreferenceStore()
						.getBoolean(UIPreferences.STAGING_VIEW_FILENAME_MODE)));
		enableAutoExpand(unstagedViewer);
		addListenerToDisableAutoExpandOnCollapse(unstagedViewer);

