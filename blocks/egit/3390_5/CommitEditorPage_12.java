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

		updateSectionClient(branchSection, branchesArea, toolkit);
	}

	private void fillBranches() {
		Repository repository = getCommit().getRepository();
		RevCommit commit = getCommit().getRevCommit();
		RevWalk revWalk = new RevWalk(repository);
		List<Ref> result = new ArrayList<Ref>();
		try {
			Map<String, Ref> refsMap = new HashMap<String, Ref>();
			refsMap.putAll(repository.getRefDatabase().getRefs(
					Constants.R_HEADS));
			refsMap.putAll(repository.getRefDatabase().getRefs(
					Constants.R_REMOTES));
			for (Ref ref : refsMap.values()) {
				if (ref.isSymbolic())
					continue;
				RevCommit headCommit = revWalk.parseCommit(ref.getObjectId());
				RevCommit base = revWalk.parseCommit(commit);
				if (revWalk.isMergedInto(base, headCommit))
					result.add(ref);
			}
		} catch (IOException ignored) {
		}

		branchViewer.setInput(result);
		branchSection.setText(MessageFormat.format(
				UIText.CommitEditorPage_SectionBranches,
				Integer.valueOf(result.size())));
	}
