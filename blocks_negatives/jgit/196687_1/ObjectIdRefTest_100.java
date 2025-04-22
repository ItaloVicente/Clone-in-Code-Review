		assertNull("no id on new ref", r.getObjectId());
		assertFalse("not peeled", r.isPeeled());
		assertNull("no peel id", r.getPeeledObjectId());
		assertSame("leaf is this", r, r.getLeaf());
		assertSame("target is this", r, r.getTarget());
		assertFalse("not symbolic", r.isSymbolic());
