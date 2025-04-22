			File linkedhello = new File(localDb.getWorkTree(), "LinkedHello");
			assertTrue("The LinkedHello file should exist",
					localDb.getFS().exists(linkedhello));
			assertTrue("The LinkedHello file should be a symlink",
					localDb.getFS().isSymLink(linkedhello));
			assertEquals("foo/hello.txt",
					localDb.getFS().readSymLink(linkedhello));
