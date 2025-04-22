		oldCommitId = git.commit().setMessage("Initial commit").call().getId();
		git.checkout().setName(BRANCH).setCreateBranch(true).call();
		git.checkout().setName("master").call();
		JGitTestUtil.writeTrashFile(defaultDb
		git.add().addFilepattern("hello.txt").call();
		git.commit().setMessage("Second commit").call();
