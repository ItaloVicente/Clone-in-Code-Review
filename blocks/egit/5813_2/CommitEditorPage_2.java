
		loadSections();
	}

	private List<Ref> loadTags() {
		RepositoryCommit repoCommit = getCommit();
		RevCommit commit = repoCommit.getRevCommit();
		Repository repository = repoCommit.getRepository();
		List<Ref> tags = new ArrayList<Ref>();
		for (Ref tag : getTags()) {
			tag = repository.peel(tag);
			ObjectId id = tag.getPeeledObjectId();
			if (id == null)
				id = tag.getObjectId();
			if (!commit.equals(id))
				continue;
			tags.add(tag);
		}
		return tags;
	}

	private List<Ref> loadBranches() {
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
		return result;
	}

	private void loadSections() {
		RepositoryCommit commit = getCommit();
		Job refreshJob = new Job(MessageFormat.format(
				UIText.CommitEditorPage_JobName, commit.getRevCommit().name())) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				final List<Ref> tags = loadTags();
				final List<Ref> branches = loadBranches();
				final FileDiff[] diffs = getCommit().getDiffs();

				final ScrolledForm form = getManagedForm().getForm();
				if (UIUtils.isUsable(form))
					form.getDisplay().syncExec(new Runnable() {

						public void run() {
							if (!UIUtils.isUsable(form))
								return;

							fillTags(getManagedForm().getToolkit(), tags);
							fillDiffs(diffs);
							fillBranches(branches);
							form.layout(true, true);
						}
					});

				return Status.OK_STATUS;
			}
		};
		refreshJob.setRule(this);
		refreshJob.schedule();
