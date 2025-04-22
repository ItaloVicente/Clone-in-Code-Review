		Map<String, Ref> allRefs = db.getAllRefs();
		Ref refHEAD = allRefs.get("refs/remotes/origin/HEAD");
		assertNotNull(refHEAD);
		assertEquals(masterId, refHEAD.getObjectId());
		assertFalse(refHEAD.isPeeled());
		assertNull(refHEAD.getPeeledObjectId());

		Ref refmaster = allRefs.get("refs/remotes/origin/master");
		assertEquals(masterId, refmaster.getObjectId());
		assertFalse(refmaster.isPeeled());
		assertNull(refmaster.getPeeledObjectId());
