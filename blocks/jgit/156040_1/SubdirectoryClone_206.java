	private final File repoDir;
	private final String origin;
	private final CredentialsProvider credentialsProvider;
	private final KetchLeaderCache leaders;
	private final File hookDir;
	private final boolean sslVerify;

	private Logger logger = LoggerFactory.getLogger(SubdirectoryClone.class);
	private List<String> branches;
	private String subdirectory;

	public SubdirectoryClone(final File directory
			final List<String> branches
			final File hookDir) {
		this(directory
				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
	}

	public SubdirectoryClone(final File directory
			final List<String> branches
			final File hookDir
		this.subdirectory = ensureTrailingSlash(subdirectory);
		this.branches = branches;
		this.repoDir = checkNotNull("directory"
		this.origin = checkNotEmpty("origin"
		this.credentialsProvider = credentialsProvider;
		this.leaders = leaders;
		this.hookDir = hookDir;
		this.sslVerify = sslVerify;
	}

	private static String ensureTrailingSlash(String subdirectory) {
		if (subdirectory.endsWith("/")) {
			return subdirectory;
		} else {
			return subdirectory + "/";
		}
	}

	public Git execute() throws IOException {
		final Git git = new Clone(repoDir
				.execute().get();
		final Repository repository = git.getRepository();

		try (final ObjectReader reader = repository.newObjectReader();
				final ObjectInserter inserter = repository.newObjectInserter()) {
			final Map<ObjectId
			final RevWalk revWalk = createRevWalk(repository
			transformBranches(repository
			overrideBranchNames(repository

			removeOriginRemote(repository);

			return git;
		} catch (Exception e) {
			String message = String.format("Error cloning origin <%s> with subdirectory <%s>."
			logger.error(message);
			cleanupDir(git.getRepository().getDirectory());
			throw new Clone.CloneException(message
		}
	}

	private void removeOriginRemote(Repository repository) throws GitAPIException {
		final org.eclipse.jgit.api.Git git = org.eclipse.jgit.api.Git.wrap(repository);
		final RemoteRemoveCommand cmd = git.remoteRemove();
		cmd.setRemoteName(origin);
		cmd.call();
	}

	private void overrideBranchNames(final Repository repository
			final Map<ObjectId
			throws AmbiguousObjectException
			GitAPIException
		for (String branchName : branches) {
			if (branchName.equals("HEAD")) {
				continue;
			}

			final ObjectId oldBranchTipId = repository.resolve(branchName);
			final ObjectId newBranchTipId = closestMappedAncestorOrSelf(commitMap
					revWalk.parseCommit(oldBranchTipId))[0];
			final RevCommit newBranchTip = revWalk.parseCommit(newBranchTipId);
			org.eclipse.jgit.api.Git.wrap(repository).branchCreate().setName(branchName).setForce(true)
					.setStartPoint(newBranchTip).setUpstreamMode(SetupUpstreamMode.NOTRACK).call();
		}
	}

	private void transformBranches(final Repository repository
			final ObjectInserter inserter
			throws MissingObjectException
			UnmergedPathException {
		for (final RevCommit commit : revWalk) {
			try {
				final Optional<ObjectId> oNewCommitTree = filterCommitTree(reader
				if (oNewCommitTree.isPresent()) {
					final ObjectId newCommitTree = oNewCommitTree.get();
					final CommitBuilder commitBuilder = generateNewCommit(commitMap
					final ObjectId newCommitId = inserter.insert(commitBuilder);

					if (isOrphanCommit(commitBuilder) || isMergeCommit(commitBuilder)
							|| isDifferentFromParent(revWalk
						commitMap.put(commit.getId()
					}
				}
			} catch (Throwable t) {
				throw new RuntimeException(String.format("Problem occurred for commit [%s]."
						t);
			}
		}
	}

	private boolean isOrphanCommit(final CommitBuilder commitBuilder) {
		return commitBuilder.getParentIds().length == 0;
	}

	private boolean isDifferentFromParent(final RevWalk revWalk
			throws MissingObjectException
		final ObjectId parentId = commitBuilder.getParentIds()[0];
		final RevCommit parentCommit = revWalk.parseCommit(parentId);
		final ObjectId parentTreeId = parentCommit.getTree().getId();
		final ObjectId commitTreeId = commitBuilder.getTreeId();
		return !commitTreeId.equals(parentTreeId);
	}

	private boolean isMergeCommit(final CommitBuilder commitBuilder) {
		return commitBuilder.getParentIds().length > 1;
	}

	private Optional<ObjectId> filterCommitTree(final ObjectReader reader
			final RevCommit commit) throws MissingObjectException
			IOException
		final DirCache dc = DirCache.newInCore();
		final DirCacheEditor editor = dc.editor();
		@SuppressWarnings("resource")
		final TreeWalk treeWalk = new TreeWalk(reader);
		int treeId = treeWalk.addTree(commit.getTree());
		treeWalk.setRecursive(true);
		boolean empty = true;
		while (treeWalk.next()) {
			final String pathString = treeWalk.getPathString();
			final CanonicalTreeParser treeParser = treeWalk.getTree(treeId
			if (inSubdirectory(pathString)) {
				moveFromSubdirectoryToRoot(editor
				empty = false;
			}
		}
		editor.finish();

		if (empty) {
			return Optional.empty();
		} else {
			return Optional.of(dc.writeTree(inserter));
		}
	}

	private RevWalk createRevWalk(final Repository repository
			throws MissingObjectException
		final RevWalk revWalk = new RevWalk(reader);
		final List<RevCommit> branchTips = getBranchCommits(repository
		revWalk.markStart(branchTips);

		revWalk.sort(RevSort.TOPO
		revWalk.sort(RevSort.REVERSE

		revWalk.setRevFilter(RevFilter.ALL);
		revWalk.setTreeFilter(TreeFilter.ALL);

		return revWalk;
	}

	private List<RevCommit> getBranchCommits(final Repository repository
		final List<RevCommit> branchTips = branches.stream().map(b -> {
			try {
				return revWalk.parseCommit(repository.resolve(b));
			} catch (IOException ioe) {
				throw new IllegalArgumentException(
						format("Unable to parse branch [%s] in repository [%s]."
			}
		}).collect(toList());
		return branchTips;
	}

	private CommitBuilder generateNewCommit(final Map<ObjectId
			final ObjectId newCommitTree) {
		final CommitBuilder commitBuilder = new CommitBuilder();
		commitBuilder.setAuthor(commit.getAuthorIdent());
		commitBuilder.setCommitter(commit.getCommitterIdent());
		commitBuilder.setTreeId(newCommitTree);
		commitBuilder.setMessage(commit.getFullMessage());
		commitBuilder.setEncoding(commit.getEncoding());
		final ObjectId[] newParentIds = closestMappedAncestorOrSelf(commitMap
		if (newParentIds.length > 0) {
			commitBuilder.setParentIds(newParentIds);
		}

		return commitBuilder;
	}

	private ObjectId[] closestMappedAncestorOrSelf(final Map<ObjectId
		final Queue<RevCommit> commitQueue = new LinkedList<>();
		final Set<ObjectId> processed = new HashSet<>();
		commitQueue.addAll(Arrays.asList(start));

		final List<ObjectId> results = new ArrayList<>();

		while (!commitQueue.isEmpty()) {
			final RevCommit cur = commitQueue.poll();
			if (!processed.contains(cur.getId())) {
				final ObjectId mappedId = commitMap.get(cur.getId());

				if (mappedId != null) {
					results.add(mappedId);
				} else {
					Arrays.stream(cur.getParents()).forEach(p -> commitQueue.add(p));
				}
				processed.add(cur.getId());
			}
		}

		return results.toArray(new ObjectId[results.size()]);
	}

	private void moveFromSubdirectoryToRoot(final DirCacheEditor editor
			final CanonicalTreeParser treeParser) {
		final String newPath = pathString.substring(subdirectory.length());
		final ObjectId entryObjectId = treeParser.getEntryObjectId();
		final FileMode entryFileMode = treeParser.getEntryFileMode();
		editor.add(new DirCacheEditor.PathEdit(new DirCacheEntry(newPath)) {

			@Override
			public void apply(DirCacheEntry ent) {
				ent.setObjectId(entryObjectId);
				ent.setFileMode(entryFileMode);
			}
		});
	}

	private boolean inSubdirectory(final String pathString) {
		return pathString.startsWith(subdirectory);
	}

	private void cleanupDir(final File gitDir) throws IOException {
		try {
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				new WindowCacheConfig().install();
			}
			FileUtils.delete(gitDir
		} catch (java.io.IOException e) {
			throw new IOException("Failed to remove the git repository."
		}
	}
