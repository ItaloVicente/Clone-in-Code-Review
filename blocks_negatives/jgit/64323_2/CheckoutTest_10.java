		Git git = new Git(db);
		writeTrashFile("a", "Hello world a");
		git.add().addFilepattern(".").call();
		git.commit().setMessage("commit file a").call();
		git.branchCreate().setName("branch_1").call();
		git.checkout().setName("branch_1").call();
		File b = writeTrashFile("b", "Hello world b");
		git.add().addFilepattern("b").call();
		git.commit().setMessage("commit file b").call();
		File a = writeTrashFile("a", "New Hello world a");
		git.add().addFilepattern(".").call();
		git.commit().setMessage("modified a").call();
		assertArrayEquals(new String[] { "" },
				execute("git checkout HEAD~2 -- a"));
		assertEquals("Hello world a", read(a));
		assertArrayEquals(new String[] { "* branch_1", "  master", "" },
				execute("git branch"));
		assertEquals("Hello world b", read(b));
