	private static final String MODIFIED_FILE_CONTENT = "modified_content";

	private Git git;

	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
