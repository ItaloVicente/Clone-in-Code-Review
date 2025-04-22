
	private Repository repo;

	@Override
	public boolean isEnabled() {
		return getRepository(false) != null;
	}

	@Override
	protected void execute(IAction action) throws InvocationTargetException,
			InterruptedException {
		repo = getRepository(true);
		if (repo == null)
			return;

		if (!repo.getRepositoryState().canCheckout()) {
			MessageDialog.openError(getShell(),
					UIText.TagAction_cannotCheckout, NLS.bind(
							UIText.TagAction_repositoryState, repo
									.getRepositoryState().getDescription()));
			return;
		}

		String currentBranchName;
		try {
			currentBranchName = repo.getBranch();
		} catch (IOException e) {
			throw new InvocationTargetException(e,
					UIText.TagAction_cannotGetBranchName);
		}

		CreateTagDialog dialog = new CreateTagDialog(getShell(),
				ValidationUtils
						.getRefNameInputValidator(repo, Constants.R_TAGS),
				currentBranchName);

		RevWalk revCommits = getRevCommits();
		dialog.setRevCommitList(revCommits);

		List<Tag> tags = getRevTags();
		dialog.setExistingTags(tags);

		if (dialog.open() != IDialogConstants.OK_ID)
			return;

		final Tag tag = new Tag(repo);
		PersonIdent personIdent = new PersonIdent(repo);
		String tagName = dialog.getTagName();

		tag.setTag(tagName);
		tag.setTagger(personIdent);
		tag.setMessage(dialog.getTagMessage());

		ObjectId tagCommit = getTagCommit(dialog.getTagCommit());
		tag.setObjId(tagCommit);

		String tagJobName = NLS.bind(UIText.TagAction_creating, tagName);
		final boolean shouldMoveTag = dialog.shouldOverWriteTag();

		Job tagJob = new Job(tagJobName) {
			protected IStatus run(IProgressMonitor monitor) {
				try {
					new TagOperation(repo, tag, shouldMoveTag).execute(monitor);
				} catch (CoreException e) {
					return Activator.createErrorStatus(
							UIText.TagAction_taggingFailed, e);
				} finally {
					GitLightweightDecorator.refresh();
				}

				return Status.OK_STATUS;
			}

		};

		tagJob.setUser(true);
		tagJob.schedule();
	}

	private List<Tag> getRevTags() {
		Collection<Ref> revTags = repo.getTags().values();
		List<Tag> tags = new ArrayList<Tag>();
		for (Ref ref : revTags) {
			try {
				Tag tag = repo.mapTag(ref.getName());
				tags.add(tag);
			} catch (IOException e) {
				ErrorDialog.openError(getShell(),
						UIText.TagAction_errorDuringTagging, NLS.bind(
								UIText.TagAction_errorWhileMappingRevTag, ref
										.getName()), new Status(IStatus.ERROR,
								Activator.getPluginId(), e.getMessage(), e));
			}
		}
		return tags;
	}

	private RevWalk getRevCommits() {
		RevWalk revWalk = new RevWalk(repo);
		revWalk.sort(RevSort.COMMIT_TIME_DESC, true);
		revWalk.sort(RevSort.BOUNDARY, true);

		try {
			AnyObjectId headId = repo.resolve(Constants.HEAD);
			if (headId != null)
				revWalk.markStart(revWalk.parseCommit(headId));
		} catch (IOException e) {
			ErrorDialog.openError(getShell(),
					UIText.TagAction_errorDuringTagging,
					UIText.TagAction_errorWhileGettingRevCommits, new Status(
							IStatus.ERROR, Activator.getPluginId(), e
									.getMessage(), e));
		}

		return revWalk;
	}

	private ObjectId getTagCommit(ObjectId objectId)
			throws InvocationTargetException {
		ObjectId result = null;
		if (objectId == null) {
			try {
				result = repo.resolve(Constants.HEAD);
			} catch (IOException e) {
				throw new InvocationTargetException(e,
						UIText.TagAction_unableToResolveHeadObjectId);
			}
		} else {
			result = objectId;
		}
		return result;
