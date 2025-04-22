	public static void runActionUsingPath(TestCase test, IMenuManager mgr,
			String idPath) {
		IContributionItem item = mgr.findUsingPath(idPath);
		Assert.assertNotNull(item);
		runAction(test, item);
	}
