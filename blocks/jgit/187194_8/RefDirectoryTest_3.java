		assertNull(refDb.exactRef("refs/heads/master"));
		assertNull(refDb.exactRef("refs/tags/v1.0"));
		assertNull(refDb.exactRef("FETCH_HEAD"));
		assertNull(refDb.exactRef("NOT.A.REF.NAME"));
		assertNull(refDb.exactRef("master"));
		assertNull(refDb.exactRef("v1.0"));
