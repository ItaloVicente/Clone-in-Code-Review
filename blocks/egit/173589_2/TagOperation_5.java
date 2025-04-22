	private Boolean sign;

	private @NonNull String name = ""; //$NON-NLS-1$

	private String message;

	private RevCommit target;

	private PersonIdent tagger;

	private CredentialsProvider credentialsProvider;

	public TagOperation(@NonNull Repository repository) {
		this.repository = repository;
	}

	public TagOperation setForce(boolean force) {
		this.force = force;
		return this;
	}

	public boolean isForce() {
		return force;
	}
