		assertTrue("Fetch after clone should be up-to-date",
				result.getTrackingRefUpdates().isEmpty());
		assertEquals("origin/master should still exist", originMasterId,
				clonedRepo.resolve("refs/remotes/origin/master"));
		assertEquals("origin/HEAD should be unchanged", originHeadId,
				clonedRepo.resolve("refs/remotes/origin/HEAD"));
