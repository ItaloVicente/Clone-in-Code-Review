	private volatile boolean initialized;

	private RepositoryResolver<HttpServletRequest> resolver;

	private AsIsFileService asIs = new AsIsFileService();

	private UploadPackFactory<HttpServletRequest> uploadPackFactory = new DefaultUploadPackFactory();

	private ReceivePackFactory<HttpServletRequest> receivePackFactory = new DefaultReceivePackFactory();

	private final List<Filter> uploadPackFilters = new LinkedList<Filter>();

	private final List<Filter> receivePackFilters = new LinkedList<Filter>();
