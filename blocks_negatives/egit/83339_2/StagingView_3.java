		stagedViewer.setLabelProvider(labelProvider);

		stagedViewer.setContentProvider(createStagingContentProvider(false));
		stagedViewer.addDragSupport(
				DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK,
				new Transfer[] { LocalSelectionTransfer.getTransfer(),
						FileTransfer.getInstance() }, new StagingDragListener(
						stagedViewer));
		stagedViewer.addDropSupport(DND.DROP_MOVE,
				new Transfer[] { LocalSelectionTransfer.getTransfer() },
				new DropTargetAdapter() {
					@Override
					public void drop(DropTargetEvent event) {
						event.detail = DND.DROP_COPY;
						if (event.data instanceof IStructuredSelection) {
							final IStructuredSelection selection = (IStructuredSelection) event.data;
							stage(selection);
						}
					}

					@Override
					public void dragOver(DropTargetEvent event) {
						event.detail = DND.DROP_MOVE;
					}
				});
		stagedViewer.addOpenListener(new IOpenListener() {
			@Override
			public void open(OpenEvent event) {
				compareWith(event);
			}
		});
		stagedViewer.setComparator(
				new StagingEntryComparator(getSortCheckState(), getPreferenceStore()
						.getBoolean(UIPreferences.STAGING_VIEW_FILENAME_MODE)));
		enableAutoExpand(stagedViewer);
		addListenerToDisableAutoExpandOnCollapse(stagedViewer);
