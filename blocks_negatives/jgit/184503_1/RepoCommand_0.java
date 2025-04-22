
			DirCache index = DirCache.newInCore();
			ObjectInserter inserter = repo.newObjectInserter();

			try (RevWalk rw = new RevWalk(repo)) {
				prepareIndex(renamedProjects, index, inserter);
				ObjectId treeId = index.writeTree(inserter);
				long prevDelay = 0;
				for (int i = 0; i < LOCK_FAILURE_MAX_RETRIES - 1; i++) {
					try {
						return commitTreeOnCurrentTip(
							inserter, rw, treeId);
					} catch (ConcurrentRefUpdateException e) {
						prevDelay = FileUtils.delay(prevDelay,
								LOCK_FAILURE_MIN_RETRY_DELAY_MILLIS,
								LOCK_FAILURE_MAX_RETRY_DELAY_MILLIS);
						Thread.sleep(prevDelay);
						repo.getRefDatabase().refresh();
					}
				}
				return commitTreeOnCurrentTip(inserter, rw, treeId);
			} catch (IOException | InterruptedException e) {
				throw new ManifestErrorException(e);
			}
