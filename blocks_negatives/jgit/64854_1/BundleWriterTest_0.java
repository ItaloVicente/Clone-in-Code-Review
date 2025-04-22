		bundle = makeBundle("refs/heads/cc", db.resolve("c").name(),
				new RevWalk(db).parseCommit(db.resolve("a").toObjectId()));
		fetchResult = fetchFromBundle(newRepo, bundle);
		advertisedRef = fetchResult.getAdvertisedRef("refs/heads/cc");
		assertEquals(db.resolve("c").name(), advertisedRef.getObjectId().name());
		assertEquals(db.resolve("c").name(), newRepo.resolve("refs/heads/cc")
				.name());
		assertNull(newRepo.resolve("refs/heads/c"));

		try {
			Repository newRepo2 = createBareRepository();
			fetchResult = fetchFromBundle(newRepo2, bundle);
			fail("We should not be able to fetch from bundle with prerequisites that are not fulfilled");
		} catch (MissingBundlePrerequisiteException e) {
			assertTrue(e.getMessage()
					.indexOf(db.resolve("refs/heads/a").name()) >= 0);
