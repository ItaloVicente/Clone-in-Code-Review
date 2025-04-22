		tree.setContentProvider(treeContentProvider);
		tree.setLabelProvider(new DecoratingLabelProvider(new WorkbenchLabelProvider(),
				IDEWorkbenchPlugin.getDefault().getWorkbench().getDecoratorManager().getLabelDecorator()));
		tree.setInput(IDEWorkbenchPlugin.getPluginWorkspace().getRoot());
		tree.setComparator(new ResourceComparator(ResourceComparator.NAME));

		data = new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL);
		data.heightHint = SIZING_SELECTION_WIDGET_HEIGHT;
		data.widthHint = SIZING_SELECTION_WIDGET_WIDTH;
		tree.getControl().setLayoutData(data);

		tree.addCheckStateListener(event -> handleCheckStateChange(event));

		tree.addTreeListener(new ITreeViewerListener() {
			@Override
