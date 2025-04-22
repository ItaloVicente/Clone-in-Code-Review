	private PrintWriter errw;

	public CLIGitCommand(Repository db) {
		super();
		this.db = db;
		result = new Result();
