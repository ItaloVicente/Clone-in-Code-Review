		if (repo.isBare()) {
			DirCache index = DirCache.newInCore();
			DirCacheBuilder builder = index.builder();
			ObjectInserter inserter = repo.newObjectInserter();
			RevWalk rw = new RevWalk(repo);

			try {
				Config cfg = new Config();
				for (Project proj : bareProjects) {
					String name = proj.path;
					String uri = proj.name;
					cfg.setString("submodule"
					cfg.setString("submodule"
					final DirCacheEntry dcEntry = new DirCacheEntry(name);
					ObjectId objectId;
					try {
						objectId = callback.sha1(uri);
					} catch (GitAPIException e) {
						throw new RemoteUnavailableException(uri
					} catch (IllegalArgumentException e) {
						throw new ManifestErrorException(e);
					}
					if (objectId == null)
						throw new RemoteUnavailableException(uri
					dcEntry.setObjectId(objectId);
					dcEntry.setFileMode(FileMode.GITLINK);
					builder.add(dcEntry);
				}
				String content = cfg.toText();

				final DirCacheEntry dcEntry = new DirCacheEntry(Constants.DOT_GIT_MODULES);
				ObjectId objectId = inserter.insert(Constants.OBJ_BLOB
						content.getBytes(Constants.CHARACTER_ENCODING));
				dcEntry.setObjectId(objectId);
				dcEntry.setFileMode(FileMode.REGULAR_FILE);
				builder.add(dcEntry);

				builder.finish();
				ObjectId treeId = index.writeTree(inserter);

				CommitBuilder commit = new CommitBuilder();
				commit.setTreeId(treeId);
				if (headId != null)
					commit.setParentIds(headId);
				commit.setAuthor(author);
				commit.setCommitter(author);
				commit.setMessage(RepoText.get().repoCommitMessage);

				ObjectId commitId = inserter.insert(commit);
				inserter.flush();

				RefUpdate ru = repo.updateRef(Constants.HEAD);
				ru.setNewObjectId(commitId);
				ru.setExpectedOldObjectId(headId != null ? headId : ObjectId.zeroId());
				Result rc = ru.update(rw);

				switch (rc) {
					case NEW:
					case FORCED:
					case FAST_FORWARD:
						break;
					case REJECTED:
					case LOCK_FAILURE:
						throw new ConcurrentRefUpdateException(
								JGitText.get().couldNotLockHEAD
								rc);
					default:
						throw new JGitInternalException(MessageFormat.format(
								JGitText.get().updatingRefFailed
								Constants.HEAD
				}

				return rw.parseCommit(commitId);
			} catch (IOException e) {
				throw new ManifestErrorException(e);
			} finally {
				rw.release();
			}
		} else {
			return git
				.commit()
				.setMessage(RepoText.get().repoCommitMessage)
				.call();
		}
