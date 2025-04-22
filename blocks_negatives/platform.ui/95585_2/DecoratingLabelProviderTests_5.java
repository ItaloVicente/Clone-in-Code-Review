    public static Test suite() {
        return new DecoratingLabelProviderTests();
    }

    public DecoratingLabelProviderTests() {
    	addTestSuite(CompositeLabelProviderTableTest.class);
    	addTestSuite(DecoratingLabelProviderTreePathTest.class);
        addTestSuite(DecoratingLabelProviderTreeTest.class);
        addTestSuite(ColorAndFontLabelProviderTest.class);
        addTestSuite(ColorAndFontViewerLabelProviderTest.class);
        addTestSuite(DecoratingStyledCellLabelProviderTest.class);
        addTestSuite(IDecorationContextTest.class);
    }
