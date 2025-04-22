	public void filter(@NonNull FilterableNode node) {
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
		ViewerCell cell = viewer.getCell(node, 0);
		if (cell == null) {
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
		String pattern = node.getFilter();
		Rectangle cellBounds = cell.getBounds();
		Rectangle area = viewer.getTree().getClientArea();
		cellBounds.width = Math.min(cellBounds.width, area.width);
		cellBounds.x = 0;
		AtomicReference<String> currentPattern = new AtomicReference<>();
		UIJob refresher = new UIJob(UIText.RepositoriesView_FilterJob) {

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
		if (pattern != null) {
			field.setText(pattern);
			field.selectAll();
		}
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
					field.removeFocusListener(closeOnFocusLost);
					container.setVisible(false);
					container.dispose();
					viewer.getTree().setFocus();
				}
			}
		});
		Point containerSize = container.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		Rectangle containerBounds = new Rectangle(
				Math.max(0, cellBounds.x + cellBounds.width - containerSize.x),
				cellBounds.y, containerSize.x, containerSize.y);
		container.setBounds(containerBounds);
		container.getDisplay().asyncExec(() -> {
			if (!container.isDisposed()) {
				container.setVisible(true);
				filterField = field;
				field.setFocus();
				field.addFocusListener(closeOnFocusLost);
			}
		});
	}

	private void filter(CommonViewer viewer, FilterableNode filterNode,
			String filter) {
		FilterCache.INSTANCE.set(filterNode, filter);
		try {
			viewer.getTree().setRedraw(false);
			viewer.refresh(filterNode);
		} finally {
			viewer.getTree().setRedraw(true);
		}
	}

