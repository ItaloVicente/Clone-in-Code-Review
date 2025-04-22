	private static final String TARGET_GIT = "target/target"

	@Test
	public void cloneSubdirectorySingleBranch() throws Exception {
		final File parentFolder = createTempDirectory();

		final File sourceDir = new File(parentFolder

		final File targetDir = new File(parentFolder

		final Git origin = gitRepo(sourceDir);
		commit(origin
		commit(origin
		commit(origin

		final Git cloned = new SubdirectoryClone(targetDir
				singletonList("master")

		assertThat(origin.getRepository().getRemoteNames()).isEmpty();

		assertThat(cloned).isNotNull();
		assertThat(listRefs(cloned)).hasSize(1);

		final List<RevCommit> cloneCommits = getCommits(cloned
		assertThat(cloneCommits).hasSize(1);

		final RevCommit clonedCommit = cloneCommits.get(0);
		final RevCommit originCommit = getCommits(origin

		assertClonedCommitData(origin
	}

	@Test
	public void cloneSubdirectoryMultipleBranches() throws Exception {
		final File parentFolder = createTempDirectory();

		final File sourceDir = new File(parentFolder

		final File targetDir = new File(parentFolder

		final Git origin = gitRepo(sourceDir);
		commit(origin
				content("file3.txt"

		branch(origin
		commit(origin

		branch(origin
		commit(origin

		final Git cloned = new SubdirectoryClone(targetDir
				asList("master"

		assertThat(cloned).isNotNull();
		final Set<String> clonedRefs = listRefs(cloned).stream().map(ref -> ref.getName()).collect(toSet());
		assertThat(clonedRefs).hasSize(2);
		assertThat(clonedRefs).containsExactly("refs/heads/master"

		{
			final List<RevCommit> cloneCommits = getCommits(cloned
			assertThat(cloneCommits).hasSize(1);
			assertClonedCommitData(origin
		}

		{
			final List<RevCommit> cloneCommits = getCommits(cloned
			assertThat(cloneCommits).hasSize(2);

			final List<RevCommit> originCommits = getCommits(origin
			assertClonedCommitData(origin
			assertClonedCommitData(origin
		}
	}

	@Test
	public void cloneSubdirectoryWithMergeCommit() throws Exception {
		final File parentFolder = createTempDirectory();

		final File sourceDir = new File(parentFolder

		final File targetDir = new File(parentFolder

		final Git origin = gitRepo(sourceDir);
		commit(origin
				content("file3.txt"

		branch(origin
		commit(origin

		commit(origin

		mergeCommit(origin
				content("dir2/file2.txt"

		final Git cloned = new SubdirectoryClone(targetDir
				asList("master"

		assertThat(cloned).isNotNull();
		final Set<String> clonedRefs = listRefs(cloned).stream().map(ref -> ref.getName()).collect(toSet());
		assertThat(clonedRefs).hasSize(2);
		assertThat(clonedRefs).containsExactly("refs/heads/master"

		{
			final List<RevCommit> cloneCommits = getCommits(cloned

			final List<RevCommit> originCommits = getCommits(origin
			assertClonedCommitData(origin
			assertClonedCommitData(origin
			assertClonedCommitData(origin
			assertClonedCommitData(origin

			assertThat(cloneCommits.get(0).getParentCount()).isEqualTo(2);
			assertThat(cloneCommits.get(1).getParentCount()).isEqualTo(1);
			assertThat(cloneCommits.get(2).getParentCount()).isEqualTo(1);
			assertThat(cloneCommits.get(3).getParentCount()).isEqualTo(0);
		}

		{
			final List<RevCommit> cloneCommits = getCommits(cloned
			assertThat(cloneCommits).hasSize(2);

			final List<RevCommit> originCommits = getCommits(origin
			assertClonedCommitData(origin
			assertClonedCommitData(origin
		}
	}

	@Test
	public void cloneSubdirectoryWithHookDir() throws Exception {
		final File hooksDir = createTempDirectory();

		writeMockHook(hooksDir
		writeMockHook(hooksDir

		final File parentFolder = createTempDirectory();

		final File sourceDir = new File(parentFolder

		final File targetDir = new File(parentFolder

		final Git origin = gitRepo(sourceDir);
		commit(origin
		commit(origin
		commit(origin

		final Git cloned = new SubdirectoryClone(targetDir
				singletonList("master")

		assertThat(origin.getRepository().getRemoteNames()).isEmpty();

		assertThat(cloned).isNotNull();
		assertThat(listRefs(cloned)).hasSize(1);

		final List<RevCommit> cloneCommits = getCommits(cloned
		assertThat(cloneCommits).hasSize(1);

		final RevCommit clonedCommit = cloneCommits.get(0);
		final RevCommit originCommit = getCommits(origin

		assertClonedCommitData(origin

		boolean foundPreCommitHook = false;
		boolean foundPostCommitHook = false;
		File[] hooks = new File(cloned.getRepository().getDirectory()
		assertThat(hooks).isNotEmpty().isNotNull();
		assertThat(hooks.length).isEqualTo(2);
		for (File hook : hooks) {
			if (hook.getName().equals(PreCommitHook.NAME)) {
				foundPreCommitHook = hook.canExecute();
			} else if (hook.getName().equals(PostCommitHook.NAME)) {
				foundPostCommitHook = hook.canExecute();
			}
		}
		assertThat(foundPreCommitHook).isTrue();
		assertThat(foundPostCommitHook).isTrue();
	}

	private void assertClonedCommitData(final Git origin
			final RevCommit originCommit) throws Exception {
		assertThat(clonedCommit.getFullMessage()).isEqualTo(originCommit.getFullMessage());

		final PersonIdent authorIdent = clonedCommit.getAuthorIdent();
		final PersonIdent commiterIdent = clonedCommit.getCommitterIdent();
		assertThat(authorIdent).isEqualTo(commiterIdent);
		assertThat(authorIdent.getName()).isEqualTo("name");
		assertThat(authorIdent.getEmailAddress()).isEqualTo("name@example.com");

		final ObjectId originDirId = findIdForPath(origin
		final ObjectId clonedTreeId = clonedCommit.getTree().getId();
		assertThat(clonedTreeId).isEqualTo(originDirId);
	}

	private ObjectId findIdForPath(final Git origin
			throws Exception {
		try (TreeWalk treeWalk = new TreeWalk(origin.getRepository())) {
			final int treeId = treeWalk.addTree(originMasterTip.getTree());
			treeWalk.setRecursive(false);
			final CanonicalTreeParser treeParser = treeWalk.getTree(treeId
			while (treeWalk.next()) {
				final String path = treeParser.getEntryPathString();
				if (path.equals(searchPath)) {
					return treeParser.getEntryObjectId();
				}
			}
		}

		throw new AssertionError(
				String.format("Could not find path [%s] in commit [%s]."
	}

	private List<RevCommit> getCommits(final Git git
		List<RevCommit> commits = new ArrayList<>();
		try (RevWalk revWalk = new RevWalk(git.getRepository())) {
			final RevCommit branchTip = revWalk.parseCommit(git.getRepository().resolve(branch));
			revWalk.markStart(branchTip);
			revWalk.sort(RevSort.TOPO);
			final Iterator<RevCommit> iter = revWalk.iterator();
			while (iter.hasNext()) {
				commits.add(iter.next());
			}
		}
		return commits;
	}

	private Git gitRepo(File gitSource) throws IOException {
		return new CreateRepository(gitSource).execute().get();
	}

	private void mergeCommit(final Git origin
			final TestFile... testFiles) throws Exception {
		final Repository repo = origin.getRepository();
		final org.eclipse.jgit.api.Git git = org.eclipse.jgit.api.Git.wrap(repo);

		final ObjectId targetId = repo.resolve(targetBranchName);
		final ObjectId sourceId = repo.resolve(sourceBranchName);

		final DirCache dc = DirCache.newInCore();
		final DirCacheEditor editor = dc.editor();

		try (ObjectInserter inserter = repo.newObjectInserter()) {
			final ObjectId treeId = writeTestFilesToTree(dc
			final ObjectId commitId = writeCommit(inserter
			updateBranch(targetBranchName
		}
	}

	private void updateBranch(final String targetBranchName
			final ObjectId commitId) throws Exception {
		git.branchCreate().setName(targetBranchName).setStartPoint(commitId.name()).setForce(true).call();
	}

	private ObjectId writeCommit(final ObjectInserter inserter
			final ObjectId... parentIds) throws IOException {
		final CommitBuilder builder = new CommitBuilder();
		builder.setAuthor(new PersonIdent("name"
		builder.setCommitter(new PersonIdent("name"
		builder.setTreeId(commitTreeId);
		builder.setMessage("merge commit");
		builder.setParentIds(parentIds);

		final ObjectId commitId = inserter.insert(builder);
		return commitId;
	}

	private ObjectId writeTestFilesToTree(final DirCache dc
			final TestFile... testFiles) throws Exception {
		for (TestFile data : testFiles) {
			writeBlob(editor
		}
		editor.finish();
		final ObjectId commitTreeId = dc.writeTree(inserter);
		return commitTreeId;
	}

	private void writeBlob(final DirCacheEditor editor
		final ObjectId blobId = inserter.insert(Constants.OBJ_BLOB
				IOUtils.toInputStream(data.content
		editor.add(new PathEdit(data.path) {
			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.REGULAR_FILE);
				ent.setObjectId(blobId);
			}
		});
	}
