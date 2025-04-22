	protected static class WorkTreeUpdater implements Closeable {

		public static class Result {

			private final List<String> modifiedFiles = new LinkedList<>();

			private final List<String> failedToDelete = new LinkedList<>();

			private ObjectId treeId = null;

			public ObjectId getTreeId() {
				return treeId;
			}

			public List<String> getFailedToDelete() {
				return failedToDelete;
			}

			public List<String> getModifiedFiles() {
				return modifiedFiles;
			}
		}

		Result result = new Result();

		@Nullable
		private final Repository repo;

		private final boolean inCore;

		private final ObjectInserter inserter;

		private final ObjectReader reader;

		private DirCache dirCache;

		private boolean implicitDirCache = false;

		private DirCacheBuilder builder;

		private WorkingTreeOptions workingTreeOptions;

		private int inCoreFileSizeLimit;

		private final Map<String

		private final TreeMap<String

		private Map<String

		private Map<String

		private boolean indexChangesWritten;

		private WorkTreeUpdater(Repository repo
			this.repo = repo;
			this.dirCache = dirCache;

			this.inCore = false;
			this.inserter = repo.newObjectInserter();
			this.reader = inserter.newReader();
			Config config = repo.getConfig();
			this.workingTreeOptions = config.get(WorkingTreeOptions.KEY);
			this.inCoreFileSizeLimit = getInCoreFileSizeLimit(config);
			this.checkoutMetadataByPath = new HashMap<>();
			this.cleanupMetadataByPath = new HashMap<>();
		}

		public static WorkTreeUpdater createWorkTreeUpdater(Repository repo
				DirCache dirCache) {
			return new WorkTreeUpdater(repo
		}

		private WorkTreeUpdater(Repository repo
				ObjectInserter oi) {
			this.repo = repo;
			this.dirCache = dirCache;
			this.inserter = oi;

			this.inCore = true;
			this.reader = oi.newReader();
			if (repo != null) {
				this.inCoreFileSizeLimit = getInCoreFileSizeLimit(
						repo.getConfig());
			}
		}

		public static WorkTreeUpdater createInCoreWorkTreeUpdater(
				Repository repo
			return new WorkTreeUpdater(repo
		}

		private static int getInCoreFileSizeLimit(Config config) {
			return config.getInt(ConfigConstants.CONFIG_MERGE_SECTION
					ConfigConstants.CONFIG_KEY_IN_CORE_LIMIT
		}

		public int getInCoreFileSizeLimit() {
			return inCoreFileSizeLimit;
		}

		public DirCache getLockedDirCache() throws IOException {
			if (dirCache == null) {
				implicitDirCache = true;
				if (inCore) {
					dirCache = DirCache.newInCore();
				} else {
					dirCache = nonNullRepo().lockDirCache();
				}
			}
			if (builder == null) {
				builder = dirCache.builder();
			}
			return dirCache;
		}

		public DirCacheBuildIterator createDirCacheBuildIterator() {
			return new DirCacheBuildIterator(builder);
		}

		public void writeWorkTreeChanges(boolean shouldCheckoutTheirs)
				throws IOException {
			handleDeletedFiles();

			if (inCore) {
				builder.finish();
				return;
			}
			if (shouldCheckoutTheirs) {
				checkout();
			}

			if (!builder.commit()) {
				revertModifiedFiles();
				throw new IndexWriteException();
			}
		}

		public Result writeIndexChanges() throws IOException {
			result.treeId = getLockedDirCache().writeTree(inserter);
			indexChangesWritten = true;
			return result;
		}

		public void addToCheckout(String path
				EolStreamType cleanupStreamType
				EolStreamType checkoutStreamType
				String checkoutSmudgeCommand) {
			if (entry != null) {
				toBeCheckedOut.put(path
			}
			addCheckoutMetadata(cleanupMetadataByPath
					cleanupSmudgeCommand);
			addCheckoutMetadata(checkoutMetadataByPath
					checkoutStreamType
		}

		public Map<String
			return toBeCheckedOut;
		}

		public void deleteFile(String path
				String smudgeCommand) {
			toBeDeleted.put(path
			if (file != null && file.isFile()) {
				addCheckoutMetadata(cleanupMetadataByPath
						smudgeCommand);
			}
		}

		private void addCheckoutMetadata(Map<String
				String path
			if (inCore || map == null) {
				return;
			}
			map.put(path
		}

		public EolStreamType detectCheckoutStreamType(Attributes attributes) {
			if (inCore) {
				return null;
			}
			return EolStreamTypeUtil.detectStreamType(OperationType.CHECKOUT_OP
					workingTreeOptions
		}

		private void handleDeletedFiles() {
			for (String path : toBeDeleted.descendingKeySet()) {
				File file = inCore ? null : toBeDeleted.get(path);
				if (file != null && !file.delete()) {
					if (!file.isDirectory()) {
						result.failedToDelete.add(path);
					}
				}
			}
		}

		public void markAsModified(String path) {
			result.modifiedFiles.add(path);
		}

		public List<String> getModifiedFiles() {
			return result.modifiedFiles;
		}

		private void checkout() throws NoWorkTreeException
			for (Map.Entry<String
					.entrySet()) {
				DirCacheEntry dirCacheEntry = entry.getValue();
				if (dirCacheEntry.getFileMode() == FileMode.GITLINK) {
					new File(nonNullRepo().getWorkTree()
							.mkdirs();
				} else {
					DirCacheCheckout.checkoutEntry(repo
							false
							workingTreeOptions);
					result.modifiedFiles.add(entry.getKey());
				}
			}
		}

		public void revertModifiedFiles() throws IOException {
			if (inCore) {
				result.modifiedFiles.clear();
				return;
			}
			if (indexChangesWritten) {
				return;
			}
			for (String path : result.modifiedFiles) {
				DirCacheEntry entry = dirCache.getEntry(path);
				if (entry != null) {
					DirCacheCheckout.checkoutEntry(repo
							cleanupMetadataByPath.get(path)
							workingTreeOptions);
				}
			}
		}

		@Override
		public void close() throws IOException {
			if (implicitDirCache) {
				dirCache.unlock();
			}
		}

		public void updateFileWithContent(StreamSupplier inputStream
				EolStreamType streamType
				File file) throws IOException {
			if (inCore) {
				return;
			}
			CheckoutMetadata metadata = new CheckoutMetadata(streamType
					smudgeCommand);

			try (OutputStream outputStream = new FileOutputStream(file)) {
				DirCacheCheckout.getContent(repo
						workingTreeOptions
			}
		}

		public DirCacheEntry insertToIndex(InputStream input
				FileMode fileMode
				int len
			return addExistingToIndex(insertResult(input
					path
		}

		public DirCacheEntry addExistingToIndex(ObjectId objectId
				FileMode fileMode
				int len) {
			DirCacheEntry dce = new DirCacheEntry(path
			dce.setFileMode(fileMode);
			if (lastModified != null) {
				dce.setLastModified(lastModified);
			}
			dce.setLength(inCore ? 0 : len);
			dce.setObjectId(objectId);
			builder.add(dce);
			return dce;
		}

		private ObjectId insertResult(InputStream input
				long length) throws IOException {
			try (LfsInputStream is = LfsFactory.getInstance()
					.applyCleanFilter(repo
				return inserter.insert(OBJ_BLOB
			}
		}

		@NonNull
		private Repository nonNullRepo() throws NullPointerException {
			return Objects.requireNonNull(repo
					() -> JGitText.get().repositoryIsRequired);
		}
	}

