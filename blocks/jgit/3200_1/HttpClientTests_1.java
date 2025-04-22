	private URIish smartAuthClientCertURI;

	private static String pathToServerKeyStore = null;

	private static String serverKeyStorePassword = null;

	private static String pathToSslCAInfo = null;

	private static String pathToSslKey = null;

	private static String sslKeyPassword = null;

	@BeforeClass
	public static void setUpClass() throws Exception {
		pathToServerKeyStore = KeyStoreHelper.pathToServerKeyStore();
		serverKeyStorePassword = KeyStoreHelper.serverKeyStorePassword();

		pathToSslCAInfo = KeyStoreHelper.pathToSslCAInfo();
		pathToSslKey = KeyStoreHelper.pathToSslKeyPKCS12();
		sslKeyPassword = KeyStoreHelper.sslKeyPassword();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		KeyStoreHelper.cleanUp();
	}

