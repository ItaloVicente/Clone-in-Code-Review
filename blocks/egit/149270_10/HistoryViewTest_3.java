
		Repository repo = myRepoViewUtil.lookupRepository(repoFile);

		refFilterHelper = new RefFilterHelper(repo);
		refFilterHelper.setRefFilters(refFilterHelper.getDefaults());
		refFilterHelper.resetLastSelectionStateToDefault();
	}

	private void checkout(Git git, String ref, boolean create)
			throws Exception {
		CheckoutCommand checkout = git.checkout();
		checkout.setName(ref);
		checkout.setCreateBranch(create);
		checkout.setUpstreamMode(SetupUpstreamMode.SET_UPSTREAM);
		checkout.call();
	}

	private void commitNewFile(String fileName, String commitMsg)
			throws Exception {
		IProject prj = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1);
		IFile toCreate = prj.getFile(fileName);
		toCreate.create(
				new ByteArrayInputStream(
						"Content".getBytes(prj.getDefaultCharset())),
				false, null);
		addAndCommit(toCreate, commitMsg);
	}

	private static void tag(Git git, String name) throws Exception {
		TagCommand tag = git.tag();
		tag.setName(name);
		PersonIdent committer = new PersonIdent(TestUtil.TESTCOMMITTER_NAME,
				TestUtil.TESTCOMMITTER_EMAIL);
		tag.setTagger(committer);
		Repository repo = git.getRepository();
		RevCommit headCommit = repo.parseCommit(
				repo.exactRef(Constants.HEAD).getLeaf().getObjectId());
		tag.setObjectId(headCommit);
		tag.call();
	}

	private void resetHard(Git git, String to) throws Exception {
		ResetCommand reset = git.reset();
		reset.setRef(to);
		reset.setMode(ResetType.HARD);
		reset.call();
	}

	private void push(Git git) throws Exception {
		PushCommand push = git.push();
		push.setPushAll();
		push.call();
	}

	private void fetch(Git git) throws Exception {
		FetchCommand fetch = git.fetch();
		fetch.call();
	}

	private void setupAdditionalCommits() throws Exception {
		Repository repo = myRepoViewUtil.lookupRepository(repoFile);
		Git git = Git.wrap(repo);

		createSimpleRemoteRepository(repoFile);

		checkout(git, "master", false);
		checkout(git, "testR", true);
		commitNewFile("testR.txt", "testR");
		push(git);
		resetHard(git, "HEAD~");

		checkout(git, "master", false);
		checkout(git, "testD", true);
		commitNewFile("testDa.txt", "testDa");
		push(git);
		fetch(git);
		resetHard(git, "HEAD~");
		commitNewFile("testDb.txt", "testDb");

		checkout(git, "master", false);
		checkout(git, "test1", true);
		commitNewFile("test1.txt", "test1");

		commitNewFile("test1t.txt", "test1t");
		tag(git, "TEST1t");
		resetHard(git, "HEAD~");

		checkout(git, "master", false);
		checkout(git, "test2", true);
		commitNewFile("test2.txt", "test2");

		checkout(git, "master", false);
		checkout(git, "test12", true);
		commitNewFile("test12.txt", "test12");

		checkout(git, "master", false);
