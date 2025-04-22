		assertNull(refdir.findRef("refs/heads/master"));
		assertNull(refdir.findRef("refs/tags/v1.0"));
		assertNull(refdir.findRef("FETCH_HEAD"));
		assertNull(refdir.findRef("NOT.A.REF.NAME"));
		assertNull(refdir.findRef("master"));
		assertNull(refdir.findRef("v1.0"));
