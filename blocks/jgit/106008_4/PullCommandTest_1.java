	@Test
	public void testPullFromURI() throws Exception {
		File sourceLocation = source.getRepository().getWorkTree();
		PullResult res = target.pull()
			.setRemote(sourceLocation.toURI().toString())
			.call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertTrue(res.getMergeResult().getMergeStatus().equals(
				MergeStatus.ALREADY_UP_TO_DATE));
	}

