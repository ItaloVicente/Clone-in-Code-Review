		try (
				Git git2 = Git.cloneRepository()
						.setBranch("refs/heads/master")
						.setDirectory(directory)
						.setURI(fileUri())
						.setNoCheckout(true)
						.call();
				Repository db2 = git2.getRepository()) {
			assertEquals("refs/heads/master"
			assertEquals("refs/remotes/origin/master
					allRefNames(git2.branchList().setListMode(ListMode.ALL).call()));
		}
