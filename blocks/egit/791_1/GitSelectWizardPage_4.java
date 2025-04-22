		tv = new TreeViewer(main, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.BORDER);
		RepositoriesViewContentProvider cp = new RepositoriesViewContentProvider();
		tv.setContentProvider(cp);
		GridDataFactory.fillDefaults().grab(true, true).hint(SWT.DEFAULT, 200)
				.applyTo(tv.getTree());
		new RepositoriesViewLabelProvider(tv);

		tv.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				checkPage();
			}
		});

		if (initialRepository != null) {
			List<WorkingDirNode> input = new ArrayList<WorkingDirNode>();
			WorkingDirNode node = new WorkingDirNode(null, initialRepository);
			input.add(node);
			tv.setInput(input);
			if (initialPath == null)
				tv.setSelection(new StructuredSelection(input.get(0)));
			else {
				RepositoryTreeNode parentNode = node;

				IPath fullPath = new Path(initialPath);
				IPath workdirPath = new Path(initialRepository.getWorkDir()
						.getPath());
				if (workdirPath.isPrefixOf(fullPath)) {
					IPath relPath = fullPath.removeFirstSegments(workdirPath
							.segmentCount());
					for (String segment : relPath.segments()) {
						for (Object child : cp.getChildren(parentNode)) {
							if (child instanceof FolderNode) {
								FolderNode childFolder = (FolderNode) child;
								if (childFolder.getObject().getName().equals(
										segment)) {
									parentNode = childFolder;
									break;
								}
							}
						}
					}
					tv.setSelection(new StructuredSelection(parentNode));
				}
			}
		}
		tv.getTree().setEnabled(!newProjectWizard.getSelection());
