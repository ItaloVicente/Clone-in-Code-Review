		try (Git git = new Git(repo)) {
			git.add().addFilepattern(".").call();
			git.commit().setMessage("initial").call();
			if (!branch.equals("master")) {
				git.checkout().setName(branch).setCreateBranch(true).call();
			}
		}
