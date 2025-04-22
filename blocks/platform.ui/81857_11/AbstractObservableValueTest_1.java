public class AbstractObservableValueTest {

	@Before
	public void setUp() throws Exception {
		RealmTester.setDefault(new CurrentRealm(true));
	}

	@After
	public void tearDown() throws Exception {
		RealmTester.setDefault(null);
	}

	@Test
