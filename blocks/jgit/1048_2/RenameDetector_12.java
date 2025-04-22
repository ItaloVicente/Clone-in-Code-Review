	private boolean done;

	private final Repository repo;

	private int renameScore = 60;

	private int renameLimit;

	private boolean overRenameLimit;

	public RenameDetector(Repository repo) {
		this.repo = repo;

		Config cfg = repo.getConfig();
		renameLimit = cfg.getInt("diff"
	}
