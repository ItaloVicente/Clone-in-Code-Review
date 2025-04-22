	@Test
	public void testContains() {
		IntList i = new IntList();
		i.add(1);
		i.add(4);
		assertTrue(i.contains(1));
		assertTrue(i.contains(4));
		assertFalse(i.contains(2));
	}

