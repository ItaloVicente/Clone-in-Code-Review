			assertFalse("The foo/Hello file should be skipped", foohello.exists());

			File linkedhello = new File(localDb.getWorkTree(), "LinkedHello");
			assertTrue("The LinkedHello file should exist", linkedhello.exists());
			File linkedfoohello = new File(localDb.getWorkTree(), "foo/LinkedHello");
			assertFalse("The foo/LinkedHello file should be skipped", linkedfoohello.exists());
