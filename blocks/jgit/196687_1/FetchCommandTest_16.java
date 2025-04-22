		assertTrue(result.getTrackingRefUpdates().isEmpty()
				"Fetch after clone should be up-to-date");
		assertEquals(originMasterId
				clonedRepo.resolve("refs/remotes/origin/master")
				"origin/master should still exist");
		assertEquals(originHeadId
				clonedRepo.resolve("refs/remotes/origin/HEAD")
				"origin/HEAD should be unchanged");
