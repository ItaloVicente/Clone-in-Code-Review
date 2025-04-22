		assertNull(refDb.findRef("refs/heads/master"));
		assertNull(refDb.findRef("refs/tags/v1.0"));
		assertNull(refDb.findRef("FETCH_HEAD"));
		assertNull(refDb.findRef("NOT.A.REF.NAME"));
		assertNull(refDb.findRef("master"));
		assertNull(refDb.findRef("v1.0"));
