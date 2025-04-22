
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
		}
		try (Git git = new Git(repo)) {
			for (RepoProject proj : filteredProjects) {
				addSubmodule(proj.getName(), proj.getUrl(), proj.getPath(),
						proj.getRevision(), proj.getCopyFiles(),
						proj.getLinkFiles(), git);
			}
			return git.commit().setMessage(RepoText.get().repoCommitMessage)
					.call();
		} catch (IOException e) {
			throw new ManifestErrorException(e);
		}
	}

	private void prepareIndex(List<RepoProject> projects, DirCache index,
			ObjectInserter inserter) throws IOException, GitAPIException {
		Config cfg = new Config();
		StringBuilder attributes = new StringBuilder();
		DirCacheBuilder builder = index.builder();
		for (RepoProject proj : projects) {
			String name = proj.getName();
			String path = proj.getPath();
			String url = proj.getUrl();
			ObjectId objectId;
			if (ObjectId.isId(proj.getRevision())) {
				objectId = ObjectId.fromString(proj.getRevision());
			} else {
				objectId = callback.sha1(url, proj.getRevision());
				if (objectId == null && !ignoreRemoteFailures) {
					throw new RemoteUnavailableException(url);
				}
				if (recordRemoteBranch) {
					cfg.setString("submodule", name, field, //$NON-NLS-1$
							proj.getRevision());
				}

				if (recordShallowSubmodules
						&& proj.getRecommendShallow() != null) {
					cfg.setBoolean("submodule", name, "shallow", //$NON-NLS-1$ //$NON-NLS-2$
							true);
				}
			}
			if (recordSubmoduleLabels) {
				StringBuilder rec = new StringBuilder();
				rec.append(path);
				for (String group : proj.getGroups()) {
					rec.append(group);
				}
				attributes.append(rec.toString());
			}

			URI submodUrl = URI.create(url);
			if (targetUri != null) {
				submodUrl = relativize(targetUri, submodUrl);
			}
			cfg.setString("submodule", name, "path", path); //$NON-NLS-1$ //$NON-NLS-2$
			cfg.setString("submodule", name, "url", //$NON-NLS-1$ //$NON-NLS-2$
					submodUrl.toString());

			if (objectId != null) {
				DirCacheEntry dcEntry = new DirCacheEntry(path);
				dcEntry.setObjectId(objectId);
				dcEntry.setFileMode(FileMode.GITLINK);
				builder.add(dcEntry);

				for (CopyFile copyfile : proj.getCopyFiles()) {
					RemoteFile rf = callback.readFileWithMode(url,
							proj.getRevision(), copyfile.src);
					objectId = inserter.insert(Constants.OBJ_BLOB,
							rf.getContents());
					dcEntry = new DirCacheEntry(copyfile.dest);
					dcEntry.setObjectId(objectId);
					dcEntry.setFileMode(rf.getFileMode());
					builder.add(dcEntry);
				}
				for (LinkFile linkfile : proj.getLinkFiles()) {
					String link;
						link = FileUtils.relativizeGitPath(
								linkfile.dest.substring(0,
										linkfile.dest.lastIndexOf('/')),
					} else {
					}

					objectId = inserter.insert(Constants.OBJ_BLOB,
							link.getBytes(UTF_8));
					dcEntry = new DirCacheEntry(linkfile.dest);
					dcEntry.setObjectId(objectId);
					dcEntry.setFileMode(FileMode.SYMLINK);
					builder.add(dcEntry);
				}
			}
		}
		String content = cfg.toText();

		DirCacheEntry dcEntry = new DirCacheEntry(
				Constants.DOT_GIT_MODULES);
		ObjectId objectId = inserter.insert(Constants.OBJ_BLOB,
				content.getBytes(UTF_8));
		dcEntry.setObjectId(objectId);
		dcEntry.setFileMode(FileMode.REGULAR_FILE);
		builder.add(dcEntry);

		if (recordSubmoduleLabels) {
			DirCacheEntry dcEntryAttr = new DirCacheEntry(
					Constants.DOT_GIT_ATTRIBUTES);
			ObjectId attrId = inserter.insert(Constants.OBJ_BLOB,
					attributes.toString().getBytes(UTF_8));
			dcEntryAttr.setObjectId(attrId);
			dcEntryAttr.setFileMode(FileMode.REGULAR_FILE);
			builder.add(dcEntryAttr);
		}

		builder.finish();
	}

	private RevCommit commitTreeOnCurrentTip(ObjectInserter inserter,
			RevWalk rw, ObjectId treeId)
			throws IOException, ConcurrentRefUpdateException {
		if (headId != null && rw.parseCommit(headId).getTree().getId().equals(treeId)) {
			return rw.parseCommit(headId);
