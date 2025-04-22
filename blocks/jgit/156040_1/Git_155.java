	List<TextualDiff> textualDiffRefs(final String branchA

	List<TextualDiff> textualDiffRefs(final String branchA
			final String commitIdBranchB);

	List<String> conflictBranchesChecker(final String branchA

	void squash(final String branch

	boolean commit(final String branchName
			final CommitContent content);

	List<DiffEntry> listDiffs(final String startCommitId

	List<DiffEntry> listDiffs(final ObjectId refA

	Map<String

	InputStream blobAsInputStream(final String treeRef

	RevCommit getFirstCommit(final Ref ref) throws IOException;

	List<Ref> listRefs();

	List<ObjectId> resolveObjectIds(final String... commits);

	RevCommit resolveRevCommit(final ObjectId objectId) throws IOException;

	List<RefSpec> updateRemoteConfig(final Map.Entry<String
			throws IOException

	PathInfo getPathInfo(final String branchName

	List<PathInfo> listPathContent(final String branchName

	boolean isHEADInitialized();

	void setHeadAsInitialized();

	void refUpdate(final String branch

	KetchLeader getKetchLeader();

	boolean isKetchEnabled();

	void enableKetch();

	void updateRepo(Repository repo);

	void updateLeaders(final KetchLeaderCache leaders);
