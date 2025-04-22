	@Before
	public void setUp() {
		resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

		resourceSet.getPackageRegistry().put(XpathtestPackage.eNS_URI, XpathtestPackage.eINSTANCE);
		URI uri = URI.createPlatformPluginURI("/org.eclipse.e4.emf.xpath.test/model/Test.xmi", true);
		resource = resourceSet.getResource(uri, true);
		XPathContextFactory<EObject> f = EcoreXPathContextFactory.newInstance();
		xpathContext = f.newContext(resource.getContents().get(0));
	}
