		assertEquals(db.resolve("HEAD")
	}

	@Test
	public void testCreationNullStartPoint() throws Exception {
		String startPoint = null;
		git.branchCreate().setName("topic").setStartPoint(startPoint).call();
		assertEquals(db.resolve("HEAD")
