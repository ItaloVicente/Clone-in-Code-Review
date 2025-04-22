public class StagingViewTest extends LocalRepositoryTestCase {

	private static final GitRepositoriesViewTestUtils repoViewUtil = new GitRepositoriesViewTestUtils();

	private File repositoryFile;

	private Repository repository;

	@Before
	public void before() throws Exception {
		repositoryFile = createProjectAndCommitToRepository();
		repository = lookupRepository(repositoryFile);
		TestUtil.configureTestCommitterAsUser(repository);
		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(repositoryFile);

		selectRepositoryNode();
	}

	@After
	public void after() {
		TestUtil.hideView(RepositoriesView.VIEW_ID);
		TestUtil.hideView(StagingView.VIEW_ID);
		Activator.getDefault().getRepositoryUtil().removeDir(repositoryFile);
	}
