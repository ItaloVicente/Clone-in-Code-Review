		recursiveDelete(new File(trash
		setupCase(mk("other")
				mk("other"));
		writeTrashFile("foo"
		try {
			checkout();
			fail("didn't get the expected exception");
		} catch (CheckoutConflictException e) {
			assertConflict("foo");
			assertWorkDir(mkmap("foo"
			assertIndex(mk("other"));
		}

		recursiveDelete(new File(trash
		recursiveDelete(new File(trash
		setupCase(null
		writeTrashFile("foo"
		try {
			checkout();
			fail("didn't get the expected exception");
		} catch (CheckoutConflictException e) {
			assertConflict("foo");
			assertWorkDir(mkmap("foo"
			assertIndex(mkmap());
		}

