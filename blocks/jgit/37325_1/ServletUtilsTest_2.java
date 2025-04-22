	private List<Object> mocks;

	@Before
	  public void setUp() {
		mocks = Collections.synchronizedList(new ArrayList<Object>());
	  }

	@After
	public void tearDown() {
		for (Object mock : mocks) {
			verify(mock);
		}
	}

	@Test
	public void emptyContextPath() {
		assertEquals("/foo/bar"
				mockRequest("/s/foo/bar"
		assertEquals("/foo%2Fbar"
				mockRequest("/s/foo%2Fbar"
	}

	@Test
	public void emptyServletPath() {
		assertEquals("/foo/bar"
				mockRequest("/c/foo/bar"
		assertEquals("/foo%2Fbar"
				mockRequest("/c/foo%2Fbar"
	}

	@Test
	public void trailingSlashes() {
		assertEquals("/foo/bar/"
				mockRequest("/c/s/foo/bar/"
		assertEquals("/foo/bar/"
		assertEquals("/foo%2Fbar/"
				mockRequest("/c/s/foo%2Fbar/"
		assertEquals("/foo%2Fbar/"
	}

	@Test
	public void servletPathMatchesRequestPath() {
		assertEquals(null
				mockRequest("/c/s"
	}

	private HttpServletRequest mockRequest(String uri
			String servletPath) {
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getRequestURI()).andStubReturn(uri);
		expect(req.getContextPath()).andStubReturn(contextPath);
		expect(req.getServletPath()).andStubReturn(servletPath);
		replay(req);
		mocks.add(req);
		return req;
	}

