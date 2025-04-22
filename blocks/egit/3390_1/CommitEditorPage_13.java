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

	private void createFilesArea(Composite parent, FormToolkit toolkit, int span) {
		Section files = createSection(parent, toolkit, span);
		Composite filesArea = createSectionClient(files, toolkit);

		TableViewer viewer = new TableViewer(toolkit.createTable(filesArea,
				SWT.V_SCROLL | SWT.H_SCROLL));
