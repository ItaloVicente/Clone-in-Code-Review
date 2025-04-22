	@Test
	public void testAddSubList() {
		multiList.subLists.get(0).add(0);
		multiList.subLists.get(1).add(1);
		ListChangeEventTracker<?> tracker = ListChangeEventTracker.observe(multiList);

		multiList.subLists.add(1, new WritableList<>(Arrays.asList(2, 3), Object.class));

		assertEquals(1, tracker.count);
		assertEquals(Arrays.asList(0, 2, 3, 1), multiList);
		ListDiffEntry<?>[] differences = tracker.event.diff.getDifferences();
		assertEquals(2, differences.length);
		assertEntry(differences[0], 1, true, 2);
		assertEntry(differences[1], 2, true, 3);
	}

	@Test
	public void testRemoveSubList() {
		multiList.subLists.get(0).add(0);
		multiList.subLists.get(1).add(1);
		multiList.subLists.add(1, new WritableList<>(Arrays.asList(2, 3), Object.class));
		ListChangeEventTracker<?> tracker = ListChangeEventTracker.observe(multiList);

		multiList.subLists.remove(1);

		assertEquals(1, tracker.count);
		assertEquals(Arrays.asList(0, 1), multiList);
		ListDiffEntry<?>[] differences = tracker.event.diff.getDifferences();
		assertEquals(2, differences.length);
		assertEntry(differences[0], 1, false, 2);
		assertEntry(differences[1], 2, false, 3);
	}

	@Test
	public void testMoveSubList() {
		multiList.subLists.get(0).add(0);
		multiList.subLists.get(1).add(1);
		multiList.subLists.add(1, new WritableList<>(Arrays.asList(2, 3), Object.class));
		ListChangeEventTracker<?> tracker = ListChangeEventTracker.observe(multiList);

		multiList.subLists.move(1, 2);

		assertEquals(1, tracker.count);
		assertEquals(Arrays.asList(0, 1, 2, 3), multiList);
		ListDiffEntry<?>[] differences = tracker.event.diff.getDifferences();
		assertEquals(4, differences.length);
		assertEntry(differences[0], 1, false, 2);
		assertEntry(differences[1], 2, true, 2);
		assertEntry(differences[2], 2, false, 3);
		assertEntry(differences[3], 3, true, 3);
	}

	@Test
	public void testReplaceWithSmallerSubList() {
		multiList.subLists.get(0).add(0);
		multiList.subLists.get(1).add(1);
		multiList.subLists.add(1, new WritableList<>(Arrays.asList(2, 3), Object.class));
		ListChangeEventTracker<?> tracker = ListChangeEventTracker.observe(multiList);


		WritableList<Object> toAdd = new WritableList<>(Arrays.asList(4), Object.class);
		toAdd.setStale(true);
		multiList.subLists.set(1, toAdd);

		assertEquals(1, tracker.count);
		assertEquals(Arrays.asList(0, 4, 1), multiList);
		assertTrue(multiList.isStale());
		ListDiffEntry<?>[] differences = tracker.event.diff.getDifferences();
		assertEquals(3, differences.length);
		assertEntry(differences[0], 1, false, 2);
		assertEntry(differences[1], 1, true, 4);
		assertEntry(differences[2], 2, false, 3);
	}

	@Test
	public void testReplaceWithLargerSubList() {
		multiList.subLists.get(0).add(0);
		multiList.subLists.get(1).add(1);
		multiList.subLists.add(1, new WritableList<>(Arrays.asList(2), Object.class));
		multiList.subLists.get(1).setStale(true);
		ListChangeEventTracker<?> tracker = ListChangeEventTracker.observe(multiList);

		multiList.subLists.set(1, new WritableList<>(Arrays.asList(3, 4), Object.class));

		assertEquals(1, tracker.count);
		assertEquals(Arrays.asList(0, 3, 4, 1), multiList);
		assertFalse(multiList.isStale());
		ListDiffEntry<?>[] differences = tracker.event.diff.getDifferences();
		assertEquals(3, differences.length);
		assertEntry(differences[0], 1, false, 2);
		assertEntry(differences[1], 1, true, 3);
		assertEntry(differences[2], 2, true, 4);
	}

