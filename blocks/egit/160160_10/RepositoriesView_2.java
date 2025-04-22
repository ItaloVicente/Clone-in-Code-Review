	public void filter(@NonNull FilterableNode node) {
		if (filterField != null) {
			return;
		}
		CommonViewer rawViewer = getCommonViewer();
		if (!(rawViewer instanceof RepositoriesCommonViewer)) {
			return; // Cannot happen
		}
		RepositoriesCommonViewer viewer = (RepositoriesCommonViewer) rawViewer;
		IContentProvider rawProvider = viewer.getContentProvider();
		if (!(rawProvider instanceof ITreeContentProvider)) {
			return; // Doesn't occur
		}
		ITreeContentProvider provider = (ITreeContentProvider) rawProvider;
		if (!provider.hasChildren(node)) {
			return;
		}
		TreeItem treeItem = viewer.getItem(node);
		if (treeItem == null) {
			return;
		}
		if (!viewer.getExpandedState(node)) {
			try {
				viewer.getTree().setRedraw(false);
				viewer.setExpandedState(node, true);
			} finally {
				viewer.getTree().setRedraw(true);
			}
		}
		AtomicReference<String> currentPattern = new AtomicReference<>();
		UIJob refresher = new UIJob(UIText.RepositoriesView_FilterJob) {

			@Override
			public boolean belongsTo(Object family) {
				return JobFamilies.REPO_VIEW_REFRESH.equals(family)
						|| super.belongsTo(family);
			}

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				if (!monitor.isCanceled()) {
					filter(viewer, node, currentPattern.get());
					return Status.OK_STATUS;
				}
				return Status.CANCEL_STATUS;
			}

		};
		refresher.setUser(false);
		Composite container = new Composite(viewer.getTree(), SWT.NONE);
		container.setVisible(false);
		GridLayoutFactory.fillDefaults().applyTo(container);
		Text field = new Text(container, SWT.SEARCH);
		GridData textData = GridDataFactory.fillDefaults().grab(true, false)
				.create();
		textData.minimumWidth = 150;
		field.setLayoutData(textData);
		field.setMessage(UIText.RepositoriesView_FilterMessage);
		String pattern = node.getFilter();
		if (pattern != null) {
			field.setText(pattern);
			field.selectAll();
		}
		TreeFilterEditor filterEditor = new TreeFilterEditor(viewer.getTree(),
				treeItem, 0, container);
		field.addVerifyListener(e -> e.text = Utils.firstLine(e.text));
		field.addModifyListener(e -> {
			refresher.cancel();
			currentPattern.set(field.getText());
			refresher.schedule(200L);
		});
		FocusAdapter closeOnFocusLost = new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				filterField = null;
				filterEditor.dispose();
				if (!container.isDisposed()) {
					container.setVisible(false);
					container.dispose();
				}
			}
		};
		field.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int key = SWTKeySupport.convertEventToUnmodifiedAccelerator(e);
				if (key == SWT.ESC) {
					refresher.cancel();
					currentPattern.set(pattern);
					refresher.schedule();
				}
				if (key == SWT.ESC || key == SWT.CR || key == SWT.LF
						|| e.character == '\r' || e.character == '\n') {
					filterField = null;
					filterEditor.dispose();
					field.removeFocusListener(closeOnFocusLost);
					container.setVisible(false);
					container.dispose();
					viewer.getTree().setFocus();
					viewer.setSelection(new StructuredSelection(node));
				}
			}
		});
		container.getDisplay().asyncExec(() -> {
			if (!container.isDisposed()) {
				container.setVisible(true);
				filterField = field;
				field.setFocus();
				field.addFocusListener(closeOnFocusLost);
			}
		});
	}

	private void filter(RepositoriesCommonViewer viewer,
			FilterableNode filterNode, String filter) {
		FilterCache.INSTANCE.set(filterNode, filter);
		try {
			viewer.getTree().setRedraw(false);
			TreeItem item = viewer.getItem(filterNode);
			Object currentNode = item.getData();
			if (filterNode != currentNode && filterNode.equals(currentNode)) {
				((FilterableNode) currentNode)
						.setFilter(filterNode.getFilter());
				viewer.update(currentNode, null);
			}
			viewer.refresh(filterNode);
		} finally {
			viewer.getTree().setRedraw(true);
		}
	}

