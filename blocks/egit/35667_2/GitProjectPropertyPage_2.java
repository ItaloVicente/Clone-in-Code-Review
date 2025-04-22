	private void createAttributesTables(Composite parent, Repository repository,
			String repoRelativePath) throws NoWorkTreeException, IOException {
		Group holdingGroups = new Group(parent, SWT.TOP);
		holdingGroups.setText(UIText.GitProjectPropertyPage_GroupAttributes);
		GridData layoutData = new GridData(GridData.FILL,
				GridData.VERTICAL_ALIGN_BEGINNING, true, false);
		layoutData.horizontalSpan = 2;
		holdingGroups.setLayoutData(layoutData);
		GridLayout layout = new GridLayout(1, true);
		holdingGroups.setLayout(layout);

		Collection<Attribute> attributes;
		try (TreeWalk treeWalk = new TreeWalk(repository)) {
			treeWalk.addTree(new FileTreeIterator(repository));
			treeWalk.addTree(new DirCacheIterator(repository.readDirCache()));
			if (!repoRelativePath.isEmpty()) {
				treeWalk.setFilter(PathFilter.create(repoRelativePath));
			}
			treeWalk.setRecursive(true);

			if (treeWalk.next()) {
				attributes = treeWalk.getAttributes().getAll();
			}
			else {
				attributes = Collections.emptyList();
			}
		}

		if (!attributes.isEmpty()) {
			createTable(holdingGroups, attributes, null);
		} else {
			createLabeledReadOnlyText(holdingGroups,
					UIText.GitProjectPropertyPage_LabelNone);
		}
	}

	private void createTable(Composite parent,
			final Collection<Attribute> attrs,
			String labelValue) {
		if (labelValue != null) {
			Label label = new Label(parent, SWT.NONE);
			label.setText(labelValue);
			label.setLayoutData(
					new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
							GridData.VERTICAL_ALIGN_BEGINNING, false, false));
		}
		final ListViewer attributeViewer = new ListViewer(parent,
				SWT.NO_SCROLL | SWT.HIDE_SELECTION | SWT.NO_FOCUS
						| SWT.READ_ONLY);
		attributeViewer.getList().setLayoutData(new GridData(GridData.FILL,
				GridData.VERTICAL_ALIGN_BEGINNING, true, true));
		attributeViewer.setContentProvider(new ArrayContentProvider());
		attributeViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						if (!event.getSelection().isEmpty()) {
							attributeViewer
									.setSelection(StructuredSelection.EMPTY);
						}
					}
				});

		attributeViewer.setInput(attrs);
	}

