	@Test
	public void testCreate() throws Exception {
		WritableList<Integer> writeList = new WritableList<>();
		writeList.add(44);
		IObservableList<Integer> compList = ComputedList.create(() -> new ArrayList<>(writeList));
		assertEquals(writeList, compList);
		writeList.add(55);
		assertEquals(writeList, compList);
	}

