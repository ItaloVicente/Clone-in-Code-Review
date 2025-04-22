
	protected RevCommit commitFile(String filename
		try {
			Git git = new Git(db);
			String originalBranch = git.getRepository().getFullBranch();
			if (git.getRepository().getRef(branch) == null)
				git.branchCreate().setName(branch).call();
			git.checkout().setName(branch).call();
			writeTrashFile(filename
			git.add().addFilepattern(filename).call();
			RevCommit commit = git.commit()
					.setMessage(branch + ": " + filename).call();
			if (originalBranch != null)
				git.checkout().setName(originalBranch).call();
			return commit;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (GitAPIException e) {
			throw new RuntimeException(e);
		}
	}
