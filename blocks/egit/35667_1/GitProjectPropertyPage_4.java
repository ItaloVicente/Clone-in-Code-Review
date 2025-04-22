	private void createAttributesTables(Composite parent, Repository repository,
			String string) throws NoWorkTreeException, IOException {
		Group holdingGroups = new Group(parent, SWT.TOP);
		holdingGroups.setText(UIText.GitProjectPropertyPage_GroupAttributes);
		GridData layoutData = new GridData(GridData.FILL,
				GridData.VERTICAL_ALIGN_BEGINNING, true, false);
		layoutData.horizontalSpan = 2;
		holdingGroups.setLayoutData(layoutData);
		GridLayout layout = new GridLayout(1, true);
		holdingGroups.setLayout(layout);

		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(new FileTreeIterator(repository));
		treeWalk.addTree(new DirCacheIterator(repository.readDirCache()));
		treeWalk.setFilter(PathFilter.create(string));
		treeWalk.setRecursive(true);

		final Set<Attribute> checkinAttributes;
		final Set<Attribute> checkoutAttributes;
		if (treeWalk.next()) {
			checkinAttributes = new HashSet<Attribute>(
					treeWalk.getAttributes(OperationType.CHECKIN_OP));
			checkoutAttributes = new HashSet<Attribute>(
					treeWalk.getAttributes(OperationType.CHECKOUT_OP));
		} else {
			checkinAttributes = Collections.emptySet();
			checkoutAttributes = Collections.emptySet();
		}

		if (checkinAttributes.equals(checkoutAttributes)) {
			if (!checkinAttributes.isEmpty()) {
				createTable(holdingGroups, checkinAttributes, null);
			} else {
				createLabeledReadOnlyText(holdingGroups,
						UIText.GitProjectPropertyPage_LabelNone);
			}
		} else {
			createTable(holdingGroups, checkinAttributes,
					UIText.GitProjectPropertyPage_LabelCheckinAttribute);
			createTable(holdingGroups, checkoutAttributes,
					UIText.GitProjectPropertyPage_LabelCheckoutAttribute);
		}
	}

	private void createTable(Composite parent, final Set<Attribute> attrs,
			String labelValue) {
		if (labelValue != null) {
			Label label = new Label(parent, SWT.NONE);
			label.setText(labelValue);
			label.setLayoutData(new GridData(
					GridData.HORIZONTAL_ALIGN_BEGINNING,
					GridData.VERTICAL_ALIGN_BEGINNING, false, false));
		}
		final ListViewer attributeViewer = new ListViewer(parent, SWT.NO_SCROLL
				| SWT.HIDE_SELECTION | SWT.NO_FOCUS);
		attributeViewer.getList().setLayoutData(
				new GridData(GridData.FILL, GridData.VERTICAL_ALIGN_BEGINNING,
						true, true));
		attributeViewer.setContentProvider(new ArrayContentProvider());
		attributeViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						if (!event.getSelection().isEmpty()) {
							attributeViewer
									.setSelection(StructuredSelection.EMPTY);
						}
					}
				});

		attributeViewer.setInput(attrs);
	}

