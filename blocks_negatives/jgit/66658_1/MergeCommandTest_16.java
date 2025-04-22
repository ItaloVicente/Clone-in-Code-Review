		assertEquals(MergeStatus.CONFLICTING, result.getMergeStatus());

		assertEquals("1\na\n3\n", read(new File(db.getWorkTree(), "a")));
		assertEquals("1\nb\n3\n", read(new File(db.getWorkTree(), "b")));
		assertEquals("1\nc(main)\n3\n", read(new File(db.getWorkTree(), "c")));
		assertEquals("1\nd(main)\n3\n", read(new File(db.getWorkTree(), "d/d/d")));
