	@Override
	@After
	public void tearDown() throws Exception {
		if (src != null)
			src.close();
		if (dst != null)
			dst.close();
		super.tearDown();
	}

