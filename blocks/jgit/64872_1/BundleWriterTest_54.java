		try (RevWalk rw = new RevWalk(db)) {
			bundle = makeBundle("refs/heads/cc"
					rw.parseCommit(db.resolve("a").toObjectId()));
			fetchResult = fetchFromBundle(newRepo
			advertisedRef = fetchResult.getAdvertisedRef("refs/heads/cc");
			assertEquals(db.resolve("c").name()
			assertEquals(db.resolve("c").name()
					.name());
			assertNull(newRepo.resolve("refs/heads/c"));

			try {
				Repository newRepo2 = createBareRepository();
				fetchResult = fetchFromBundle(newRepo2
				fail("We should not be able to fetch from bundle with prerequisites that are not fulfilled");
			} catch (MissingBundlePrerequisiteException e) {
				assertTrue(e.getMessage()
						.indexOf(db.resolve("refs/heads/a").name()) >= 0);
			}
