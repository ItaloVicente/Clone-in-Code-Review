	private void createBranchesArea(Composite parent, FormToolkit toolkit,
			int span) {
		branchSection = createSection(parent, toolkit, span);
		Composite branchesArea = createSectionClient(branchSection, toolkit);

		branchViewer = new TableViewer(toolkit.createTable(branchesArea,
				SWT.V_SCROLL | SWT.H_SCROLL));
		GridDataFactory.fillDefaults().grab(true, true).hint(SWT.DEFAULT, 50)
				.applyTo(branchViewer.getControl());
		branchViewer.setSorter(new ViewerSorter());
		branchViewer.setLabelProvider(new LabelProvider() {

			public Image getImage(Object element) {
				return CommitEditorPage.this.getImage(UIIcons.BRANCH);
			}

			public String getText(Object element) {
				return Repository.shortenRefName(((Ref) element).getName());
			}

		});
		branchViewer.setContentProvider(ArrayContentProvider.getInstance());

		fillBranches();
