		List<Ref> allRefs = db.getRefDatabase().getRefs();
		Optional<Ref> refHEAD = allRefs.stream()
				.filter(ref -> ref.getName().equals("refs/remotes/origin/HEAD"))
				.findAny();
		assertTrue(refHEAD.isPresent());
		assertEquals(masterId
		assertFalse(refHEAD.get().isPeeled());
		assertNull(refHEAD.get().getPeeledObjectId());

		Optional<Ref> refmaster = allRefs.stream().filter(
				ref -> ref.getName().equals("refs/remotes/origin/master"))
				.findAny();
		assertTrue(refmaster.isPresent());
		assertEquals(masterId
		assertFalse(refmaster.get().isPeeled());
		assertNull(refmaster.get().getPeeledObjectId());
