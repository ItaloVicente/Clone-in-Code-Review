	private HiddenBranchRefFilter filter;

	@Mock
	private Ref ref;
	private Map<String

	@Before
	public void setUp() {

		refs = new HashMap<>();
		refs.put("master"
		refs.put("develop"
		refs.put("PR--from/develop-master"
		refs.put("PR-1--master"
		refs.put("PR-master"
		refs.put("PR-1-from/develop-master"

		filter = new HiddenBranchRefFilter();
	}

	@Test
	public void testHiddenBranchsFiltering() {
		final Map<String
		final Set<Map.Entry<String
		assertEquals(5
		assertFalse(set.stream().anyMatch(entry -> entry.getKey().equals("PR-1-from/develop-master")));
	}
