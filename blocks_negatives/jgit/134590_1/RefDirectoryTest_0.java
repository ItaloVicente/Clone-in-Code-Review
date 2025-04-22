		assertNull(refdir.getRef("refs/heads/master"));
		assertNull(refdir.getRef("refs/tags/v1.0"));
		assertNull(refdir.getRef("FETCH_HEAD"));
		assertNull(refdir.getRef("NOT.A.REF.NAME"));
		assertNull(refdir.getRef("master"));
		assertNull(refdir.getRef("v1.0"));
