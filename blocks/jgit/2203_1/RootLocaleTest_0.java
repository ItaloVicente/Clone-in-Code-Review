public class RootLocaleTest {
	@Before
	public void setUp() {
		NLS.setLocale(NLS.ROOT_LOCALE);
	}

	@Test
	public void testJGitText() {
		NLS.getBundleFor(JGitText.class);
	}
