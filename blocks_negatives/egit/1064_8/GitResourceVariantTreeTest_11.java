		IType mainJava = createResourceAndCommit("org.egit.test", "Main.java",
				"class Main {}", "Initial commit");
		createBranch("test");
		new BranchOperation(repo, "refs/heads/test").execute(null);
		((IFile) mainJava.getResource()).appendContents(
		addAndCommitResource(mainJava, "Second commit");
