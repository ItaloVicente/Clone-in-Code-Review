				db.resolve("master:b/b2.txt"));
		assertEquals(master_txt, db.resolve(":master.txt"));
		assertEquals(b3_b2_txt, db.resolve("b~3:b/b2.txt"));

		assertNull("no FOO", db.resolve("b:FOO"));
		assertNull("no b/FOO", db.resolve("b:b/FOO"));
		assertNull("no b/FOO", db.resolve(":b/FOO"));
		assertNull("no not-a-branch:", db.resolve("not-a-branch:"));
