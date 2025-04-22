	private ObjectId obj3;
	private String refName = "refs/tags/blob";

	private ReceivePack receivePack;

	/**
	 * compilation private static final NullProgressMonitor PM =
	 * NullProgressMonitor.INSTANCE; private static final String R_MASTER =
	 * Constants.R_HEADS + Constants.MASTER; private static final String
	 * R_PRIVATE = Constants.R_HEADS + "private"; private Repository src;
	 * private Repository dst; private RevCommit A, B, P; private RevBlob a, b;
	 */

	public void setUp() throws Exception {
		super.setUp();
		server = newRepo("server");
		client = newRepo("client");
		receivePack = new ReceivePack(server);
		testProtocol = new TestProtocol<>(null,
				new ReceivePackFactory<Object>() {
					@Override
					public ReceivePack create(Object req, Repository database)
							throws ServiceNotEnabledException,
							ServiceNotAuthorizedException {
						return receivePack;
					}
				});
		uri = testProtocol.register(ctx, server);
	}
