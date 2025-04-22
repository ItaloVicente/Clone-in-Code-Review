public class CheckedInCommitsCacheTest extends LocalDiskRepositoryTestCase {

	private FileRepository db;

	private static final String INITIAL_TAG = "initial-tag";

	private static final AbbreviatedObjectId ZERO_ID = AbbreviatedObjectId
			.fromObjectId(zeroId());

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		db = createWorkRepository();
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		git.tag().setName(INITIAL_TAG).call();
	}
