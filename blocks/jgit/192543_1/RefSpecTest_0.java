
	@Test
	public void emptyDest(){
		RefSpec a = new RefSpec("refs/heads/master:");
		assertEquals(a.getSource()
		assertNull(a.getDestination());
	}

	@Test
	public void emptySource(){
		RefSpec a = new RefSpec(":refs/heads/master");
		assertNull(a.getSource());
		assertEquals(a.getDestination()
	}
