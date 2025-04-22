    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new AllTests();
    }

    public AllTests() {
        addTestSuite(ImageRegistryTest.class);
        addTestSuite(ResourceManagerTest.class);
        addTestSuite(FileImageDescriptorTest.class);
		addTestSuite(DecorationOverlayIconTest.class);
    }
