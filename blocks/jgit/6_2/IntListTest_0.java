	public void testSet() {
		final IntList i = new IntList();
		i.add(1);
		assertEquals(1
		assertEquals(1

		i.set(0
		assertEquals(5

		try {
			i.set(5
			fail("accepted set of 5 beyond end of list");
		} catch (ArrayIndexOutOfBoundsException e){
			assertTrue(true);
		}

		i.set(1
		assertEquals(2
		assertEquals(2
	}

