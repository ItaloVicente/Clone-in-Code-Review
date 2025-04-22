	@Test
	public void testCheckoutOfDirectoryShouldBeRecursive() throws Exception {
		File a = writeTrashFile("dir/a.txt"
		File b = writeTrashFile("dir/sub/b.txt"
		git.add().addFilepattern("dir").call();
		git.commit().setMessage("Added dir").call();

		write(a
		write(b
		git.checkout().addPath("dir").call();

		assertThat(read(a)
		assertThat(read(b)
	}

	@Test
	public void testCheckoutAllPaths() throws Exception {
		File a = writeTrashFile("dir/a.txt"
		File b = writeTrashFile("dir/sub/b.txt"
		git.add().addFilepattern("dir").call();
		git.commit().setMessage("Added dir").call();

		write(a
		write(b
		git.checkout().setAllPaths(true).call();

		assertThat(read(a)
		assertThat(read(b)
	}

	@Test
	public void testCheckoutWithStartPoint() throws Exception {
		File a = writeTrashFile("a.txt"
		git.add().addFilepattern("a.txt").call();
		RevCommit first = git.commit().setMessage("Added a").call();

		write(a
		git.commit().setAll(true).setMessage("Other").call();

		git.checkout().setCreateBranch(true).setName("a")
				.setStartPoint(first.getId().getName()).call();

		assertThat(read(a)
	}

	@Test
	public void testCheckoutWithStartPointOnlyCertainFiles() throws Exception {
		File a = writeTrashFile("a.txt"
		File b = writeTrashFile("b.txt"
		git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();
		RevCommit first = git.commit().setMessage("First").call();

		write(a
		write(b
		git.commit().setAll(true).setMessage("Other").call();

		git.checkout().setCreateBranch(true).setName("a")
				.setStartPoint(first.getId().getName()).addPath("a.txt").call();

		assertThat(read(a)
		assertThat(read(b)
	}

