	@BeforeClass
	public static void setup() throws Exception {
		perspective = bot.activePerspective();
		bot.perspectiveById("org.eclipse.pde.ui.PDEPerspective").activate();
		File gitDir = createProjectAndCommitToRepository();
		repo = FileRepositoryBuilder.create(gitDir);
