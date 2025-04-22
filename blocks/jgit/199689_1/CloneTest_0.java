	private RevCommit createSecondCommit() throws Exception {
		JGitTestUtil.writeTrashFile(db
		git.add().addFilepattern("Test.txt").call();
		return git.commit()
				.setCommitter(new PersonIdent(this.committer
				.setMessage("Second commit").call();
	}

	private RevCommit createThirdCommit() throws Exception {
		JGitTestUtil.writeTrashFile(db
		git.add().addFilepattern("change.txt").call();
		return git.commit()
				.setCommitter(new PersonIdent(this.committer
				.setMessage("Third commit").call();
	}

