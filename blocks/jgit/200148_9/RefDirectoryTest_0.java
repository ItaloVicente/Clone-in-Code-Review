	@Test
	public void testSnapshot_CannotSeeExternalPackedRefsUpdates()
			throws IOException {
		String refName = "refs/heads/new";
		RefDirectory.WritableSnapshot snapshot = new RefDirectory.WritableSnapshot(
				refdir);

		writePackedRef(refName
		assertEquals(A
		assertEquals(A

		writePackedRef(refName
		assertEquals(B
		assertEquals(A
	}

	@Test
	public void testSnapshot_WriteThrough() throws IOException {
		String refName = "refs/heads/new";
		RefDirectory.WritableSnapshot snapshot = new RefDirectory.WritableSnapshot(
				refdir);

		writePackedRef(refName
		assertEquals(A
		assertEquals(A

		PackedBatchRefUpdate update = snapshot.newBatchUpdate();
		update.addCommand(new ReceiveCommand(A
		update.execute(repo.getRevWalk()

		assertEquals(B
		assertEquals(B
	}

