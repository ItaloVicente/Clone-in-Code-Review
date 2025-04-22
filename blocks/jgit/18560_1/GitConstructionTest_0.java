
	@Test
	public void testClose() throws IOException
			GitAPIException {
		File workTree = db.getWorkTree();
		Git git = Git.wrap(db);
		git.gc().setExpire(null).call();
		git.checkout().setName(git.getRepository().resolve("HEAD^").getName())
				.call();
		try {
			FileUtils.delete(workTree
		} catch (IOException e) {
			git.close();
			FileUtils.delete(workTree
		}
	}
