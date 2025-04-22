		assertNull("no id on new ref", r.getObjectId());
		assertFalse("not peeled", r.isPeeled());
		assertNull("no peel id", r.getPeeledObjectId());
		assertSame("leaf is t", t, r.getLeaf());
		assertSame("target is t", t, r.getTarget());
		assertTrue("is symbolic", r.isSymbolic());
		assertTrue("holds update index", r.getUpdateIndex() == 1);
