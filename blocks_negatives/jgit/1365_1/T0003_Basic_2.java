	public void test025_packedRefs() throws IOException {
		test020_createBlobTag();
		test021_createTreeTag();
		test022_createCommitTag();

		if (!new File(db.getDirectory(),refs/tags/test020).delete()) throw new Error("Cannot delete unpacked tag");
		if (!new File(db.getDirectory(),refs/tags/test021).delete()) throw new Error("Cannot delete unpacked tag");
		if (!new File(db.getDirectory(),refs/tags/test022).delete()) throw new Error("Cannot delete unpacked tag");

		Tag mapTag20missing = db.mapTag("test020");
		assertNull(mapTag20missing);

		PrintWriter w = new PrintWriter(new FileWriter(new File(db.getDirectory(), "packed-refs")));
		w.println("# packed-refs with: peeled");
		w.println("6759556b09fbb4fd8ae5e315134481cc25d46954 refs/tags/test020");
		w.println("^e69de29bb2d1d6434b8b29ae775ad8c2e48c5391");
		w.println("b0517bc8dbe2096b419d42424cd7030733f4abe5 refs/tags/test021");
		w.println("^417c01c8795a35b8e835113a85a5c0c1c77f67fb");
		w.println("0ce2ebdb36076ef0b38adbe077a07d43b43e3807 refs/tags/test022");
		w.println("^b5d3b45a96b340441f5abb9080411705c51cc86c");
		w.close();
		((RefDirectory)db.getRefDatabase()).rescan();

		Tag mapTag20 = db.mapTag("test020");
		assertNotNull("have tag test020", mapTag20);
		assertEquals("blob", mapTag20.getType());
		assertEquals("test020 tagged\n", mapTag20.getMessage());
		assertEquals(new PersonIdent(author, 1154236443000L, -4 * 60), mapTag20.getTagger());
		assertEquals("e69de29bb2d1d6434b8b29ae775ad8c2e48c5391", mapTag20.getObjId().name());

		Tag mapTag21 = db.mapTag("test021");
		assertEquals("tree", mapTag21.getType());
		assertEquals("test021 tagged\n", mapTag21.getMessage());
		assertEquals(new PersonIdent(author, 1154236443000L, -4 * 60), mapTag21.getTagger());
		assertEquals("417c01c8795a35b8e835113a85a5c0c1c77f67fb", mapTag21.getObjId().name());

		Tag mapTag22 = db.mapTag("test022");
		assertEquals("commit", mapTag22.getType());
		assertEquals("test022 tagged\n", mapTag22.getMessage());
		assertEquals(new PersonIdent(author, 1154236443000L, -4 * 60), mapTag22.getTagger());
		assertEquals("b5d3b45a96b340441f5abb9080411705c51cc86c", mapTag22.getObjId().name());
	}

