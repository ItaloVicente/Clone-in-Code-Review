		Tag mapTag = db.mapTag("test020");
		assertEquals("blob", mapTag.getType());
		assertEquals("test020 tagged\n", mapTag.getMessage());
		assertEquals(new PersonIdent(author, 1154236443000L, -4 * 60), mapTag.getTagger());
		assertEquals("e69de29bb2d1d6434b8b29ae775ad8c2e48c5391", mapTag.getObjId().name());
	}

	public void test020b_createBlobPlainTag() throws IOException {
		test020_createBlobTag();
		Tag t = new Tag(db);
		t.setTag("test020b");
		t.setObjId(ObjectId.fromString("e69de29bb2d1d6434b8b29ae775ad8c2e48c5391"));
		t.tag();

		Tag mapTag = db.mapTag("test020b");
		assertEquals("e69de29bb2d1d6434b8b29ae775ad8c2e48c5391", mapTag.getObjId().name());

