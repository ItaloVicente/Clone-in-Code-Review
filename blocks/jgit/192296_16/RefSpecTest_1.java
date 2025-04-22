	@Test(expected = IllegalArgumentException.class)
	public void invalidNegativeAndForce() {
		assertNotNull(new RefSpec("^+refs/heads/master"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidForceAndNegative() {
		assertNotNull(new RefSpec("+^refs/heads/master"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidNegativeNoSrcDest() {
		assertNotNull(new RefSpec("^"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidNegativeBothSrcDest() {
	}

