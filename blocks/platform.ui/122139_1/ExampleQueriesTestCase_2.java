	@After
	public void tearDown() {
		xpathContext = null;
		resource.unload();
		resourceSet.getResources().remove(resource);
	}
