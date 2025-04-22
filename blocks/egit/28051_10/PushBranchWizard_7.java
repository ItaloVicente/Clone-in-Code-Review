	public PushBranchWizard(final Repository repository, Ref ref) {
		this(repository, ref.getObjectId(), ref);
	}

	public PushBranchWizard(final Repository repository, ObjectId commitToPush) {
		this(repository, commitToPush, null);
	}

	private PushBranchWizard(final Repository repository, ObjectId commitToPush, Ref ref) {
