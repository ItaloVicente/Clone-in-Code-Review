	@Test
	public void testChangeLocalAndDeleteRemote() throws Exception {
		GitResourceVariantTreeProvider provider = createTreeProviderWithChangeDeleteConflicts();
		GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				provider);

		final IDiff diff1 = subscriber.getDiff(iFile1);
		assertTrue(diff1 instanceof IThreeWayDiff);
		assertEquals(IDiff.CHANGE, diff1.getKind());
		assertEquals(IThreeWayDiff.CONFLICTING,
				((IThreeWayDiff) diff1).getDirection());
		final IDiff localDiff1 = ((IThreeWayDiff) diff1).getLocalChange();
		final IDiff remoteDiff1 = ((IThreeWayDiff) diff1).getRemoteChange();
		assertTrue(localDiff1 instanceof ResourceDiff);
		assertTrue(remoteDiff1 instanceof ResourceDiff);

		IFileRevision ancestorState1 = ((ResourceDiff) localDiff1)
				.getBeforeState();
		final IFileRevision localState1 = ((ResourceDiff) localDiff1)
				.getAfterState();
		assertTrue(iFile1.getName().equals(ancestorState1.getName()));
		assertTrue(iFile1.getName().equals(localState1.getName()));
		IStorage ancestorStorage1 = ancestorState1
				.getStorage(new NullProgressMonitor());
		assertContentEquals(ancestorStorage1, INITIAL_CONTENT_1);
		IStorage localStorage1 = localState1
				.getStorage(new NullProgressMonitor());
		assertContentEquals(localStorage1, INITIAL_CONTENT_1 + MASTER_CHANGES);

		ancestorState1 = ((ResourceDiff) remoteDiff1).getBeforeState();
		final IFileRevision remoteState1 = ((ResourceDiff) remoteDiff1)
				.getAfterState();
		assertTrue(iFile1.getName().equals(ancestorState1.getName()));
		assertNull(remoteState1);
		ancestorStorage1 = ancestorState1.getStorage(new NullProgressMonitor());
		assertContentEquals(ancestorStorage1, INITIAL_CONTENT_1);

		final IDiff diff2 = subscriber.getDiff(iFile2);
		assertTrue(diff2 instanceof IThreeWayDiff);
		assertEquals(IDiff.CHANGE, diff2.getKind());
		assertEquals(IThreeWayDiff.CONFLICTING,
				((IThreeWayDiff) diff2).getDirection());
		final IDiff localDiff2 = ((IThreeWayDiff) diff2).getLocalChange();
		final IDiff remoteDiff2 = ((IThreeWayDiff) diff2).getRemoteChange();
		assertTrue(localDiff2 instanceof ResourceDiff);
		assertTrue(remoteDiff2 instanceof ResourceDiff);

		IFileRevision ancestorState2 = ((ResourceDiff) localDiff2)
				.getBeforeState();
		final IFileRevision localState2 = ((ResourceDiff) localDiff2)
				.getAfterState();
		assertTrue(iFile2.getName().equals(ancestorState2.getName()));
		assertNull(localState2);
		IStorage ancestorStorage2 = ancestorState2
				.getStorage(new NullProgressMonitor());
		assertContentEquals(ancestorStorage2, INITIAL_CONTENT_2);

		ancestorState2 = ((ResourceDiff) remoteDiff2).getBeforeState();
		final IFileRevision remoteState2 = ((ResourceDiff) remoteDiff2)
				.getAfterState();
		assertTrue(iFile2.getName().equals(ancestorState2.getName()));
		assertTrue(iFile2.getName().equals(remoteState2.getName()));
		ancestorStorage2 = ancestorState2.getStorage(new NullProgressMonitor());
		assertContentEquals(ancestorStorage2, INITIAL_CONTENT_2);
		IStorage remoteStorage2 = remoteState2
				.getStorage(new NullProgressMonitor());
		assertContentEquals(remoteStorage2, INITIAL_CONTENT_2 + BRANCH_CHANGES);
	}

