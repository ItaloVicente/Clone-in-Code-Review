	public void test029_mapObject() throws IOException {
		assertEquals(new byte[0].getClass(), db.mapObject(ObjectId.fromString("5b6e7c66c276e7610d4a73c70ec1a1f7c1003259"), null).getClass());
		assertEquals(Commit.class, db.mapObject(ObjectId.fromString("540a36d136cf413e4b064c2b0e0a4db60f77feab"), null).getClass());
		assertEquals(Tree.class, db.mapObject(ObjectId.fromString("aabf2ffaec9b497f0950352b3e582d73035c2035"), null).getClass());
		assertEquals(Tag.class, db.mapObject(ObjectId.fromString("17768080a2318cd89bba4c8b87834401e2095703"), null).getClass());
	}

