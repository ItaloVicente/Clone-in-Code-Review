		assertNull(refdir.exactRef("refs/heads/master"));
		assertNull(refdir.exactRef("refs/tags/v1.0"));
		assertNull(refdir.exactRef("FETCH_HEAD"));
		assertNull(refdir.exactRef("NOT.A.REF.NAME"));
		assertNull(refdir.exactRef("master"));
		assertNull(refdir.exactRef("v1.0"));
