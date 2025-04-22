		try (
				Git git2 = Git.cloneRepository()
						.setBranch("refs/heads/master")
						.setDirectory(directory)
						.setURI(fileUri())
						.setBare(true)
						.call();
				Repository db2 = git2.getRepository()) {
			assertEquals("refs/heads/master"
			assertEquals("refs/heads/master
					allRefNames(git2.branchList().setListMode(ListMode.ALL).call()));
		}
