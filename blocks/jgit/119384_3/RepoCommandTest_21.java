				.getRepository()) {
			File hello = new File(localDb.getWorkTree()
			assertFalse("The Hello file shouldn't exist"
			File hellotxt = new File(localDb.getWorkTree()
			assertTrue("The Hello.txt file should exist"
			dotmodules = new File(localDb.getWorkTree()
					Constants.DOT_GIT_MODULES);
		}
