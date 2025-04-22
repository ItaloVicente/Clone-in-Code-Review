	@Override
	protected void setupNodes(int n) {
		super.setupNodes(n);
		locator=new ArrayModNodeLocator(Arrays.asList(nodes),
			HashAlgorithm.NATIVE_HASH);
	}
