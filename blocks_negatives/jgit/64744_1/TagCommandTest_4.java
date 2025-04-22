		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		try {
			git.tag().setName("bad~tag~name").setMessage("some message").call();
			fail("We should have failed due to a bad tag name");
		} catch (InvalidTagNameException e) {
