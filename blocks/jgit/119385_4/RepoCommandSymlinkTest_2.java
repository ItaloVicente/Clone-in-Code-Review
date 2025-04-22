				File linkedbarfoohello = new File(localDb.getWorkTree()
						"bar/foo/LinkedHello");
				assertTrue("The bar/foo/LinkedHello file should exist"
						localDb.getFS().exists(linkedbarfoohello));
				assertTrue("The bar/foo/LinkedHello file should be a symlink"
						localDb.getFS().isSymLink(linkedbarfoohello));
				assertEquals("../baz/hello.txt"
						localDb.getFS().readSymLink(linkedbarfoohello));
			}
