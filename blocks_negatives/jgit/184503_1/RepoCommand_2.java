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

		final DirCacheEntry dcEntry = new DirCacheEntry(
				Constants.DOT_GIT_MODULES);
		ObjectId objectId = inserter.insert(Constants.OBJ_BLOB,
				content.getBytes(UTF_8));
		dcEntry.setObjectId(objectId);
		dcEntry.setFileMode(FileMode.REGULAR_FILE);
		builder.add(dcEntry);

		if (recordSubmoduleLabels) {
			final DirCacheEntry dcEntryAttr = new DirCacheEntry(
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
		}

		CommitBuilder commit = new CommitBuilder();
		commit.setTreeId(treeId);
		if (headId != null)
			commit.setParentIds(headId);
		commit.setAuthor(author);
		commit.setCommitter(author);
		commit.setMessage(RepoText.get().repoCommitMessage);

		ObjectId commitId = inserter.insert(commit);
		inserter.flush();

		RefUpdate ru = repo.updateRef(targetBranch);
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
				throw new ConcurrentRefUpdateException(MessageFormat
						.format(JGitText.get().cannotLock, targetBranch),
						ru.getRef(), rc);
			default:
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().updatingRefFailed,
						targetBranch, commitId.name(), rc));
		}

		return rw.parseCommit(commitId);
	}

	private void addSubmodule(String name, String url, String path,
			String revision, List<CopyFile> copyfiles, List<LinkFile> linkfiles,
			Git git) throws GitAPIException, IOException {
		assert (!repo.isBare());
		assert (git != null);
		if (!linkfiles.isEmpty()) {
			throw new UnsupportedOperationException(
					JGitText.get().nonBareLinkFilesNotSupported);
		}

		SubmoduleAddCommand add = git.submoduleAdd().setName(name).setPath(path)
				.setURI(url);
		if (monitor != null)
			add.setProgressMonitor(monitor);

		Repository subRepo = add.call();
		if (revision != null) {
			try (Git sub = new Git(subRepo)) {
				sub.checkout().setName(findRef(revision, subRepo)).call();
			}
			subRepo.close();
			git.add().addFilepattern(path).call();
		}
		for (CopyFile copyfile : copyfiles) {
			copyfile.copy();
			git.add().addFilepattern(copyfile.dest).call();
		}
