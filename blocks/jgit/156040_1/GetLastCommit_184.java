	private final Git git;
	private final Ref ref;

	public GetLastCommit(final Git git
		this(git
	}

	public GetLastCommit(final Git git
		this.git = git;
		this.ref = ref;
	}

	public RevCommit execute() throws IOException {
		if (ref == null) {
			return null;
		}
		return git.resolveRevCommit(ref.getTarget().getObjectId());
	}
