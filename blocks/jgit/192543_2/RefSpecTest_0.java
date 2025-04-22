
	@Test
	public void emptyDest(){
		RefSpec a = new RefSpec("refs/heads/master:");
		assertEquals(a.getSource()
		assertNull(a.getDestination());
	}

	@Test
	public void emptySrc(){
		RefSpec a = new RefSpec(":refs/heads/master");
		assertNull(a.getSource());
		assertEquals(a.getDestination()
	}

	@Test
	public void emptySrcDest(){
		RefSpec a = new RefSpec("");
		assertNull(a.getSource());
		assertNull(a.getDestination());
	}
