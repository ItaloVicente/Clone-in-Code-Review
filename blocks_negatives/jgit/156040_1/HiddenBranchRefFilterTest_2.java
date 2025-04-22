    private HiddenBranchRefFilter filter;

    @Mock
    private Ref ref;
    private Map<String, Ref> refs;

    @Before
    public void setUp() {

        refs = new HashMap<>();
        refs.put("master",
                 ref);
        refs.put("develop",
                 ref);
        refs.put("PR--from/develop-master",
                 ref);
        refs.put("PR-1--master",
                 ref);
        refs.put("PR-master",
                 ref);
        refs.put("PR-1-from/develop-master",
                 ref);

        filter = new HiddenBranchRefFilter();
    }

    @Test
    public void testHiddenBranchsFiltering() {
        final Map<String, Ref> filteredRefs = filter.filter(refs);
        final Set<Map.Entry<String, Ref>> set = filteredRefs.entrySet();
        assertEquals(5,
                     set.size());
        assertFalse(set.stream().anyMatch(entry -> entry.getKey().equals("PR-1-from/develop-master")));
    }
