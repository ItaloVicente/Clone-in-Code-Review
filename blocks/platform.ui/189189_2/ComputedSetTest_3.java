	@Test
	public void testCreate() throws Exception {
		WritableSet<Integer> writeSet = new WritableSet<>();
		writeSet.add(44);
		IObservableSet<Integer> compSet = ComputedSet.create(() -> new HashSet<>(writeSet));
		assertEquals(writeSet, compSet);
		writeSet.add(55);
		assertEquals(writeSet, compSet);
	}

