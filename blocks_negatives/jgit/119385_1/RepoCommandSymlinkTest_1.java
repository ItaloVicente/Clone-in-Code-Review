			File linkedsubdirhello = new File(localDb.getWorkTree(),
					"subdir/LinkedHello");
			assertTrue("The subdir/LinkedHello file should exist",
					localDb.getFS().exists(linkedsubdirhello));
			assertTrue("The subdir/LinkedHello file should be a symlink",
					localDb.getFS().isSymLink(linkedsubdirhello));
			assertEquals("../foo/hello.txt",
					localDb.getFS().readSymLink(linkedsubdirhello));
