	private void prepareIndex(List<RepoProject> projects
			ObjectInserter inserter) throws IOException
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
				objectId = callback.sha1(url
				if (objectId == null && !ignoreRemoteFailures) {
					throw new RemoteUnavailableException(url);
				}
				if (recordRemoteBranch) {
					cfg.setString("submodule"
							proj.getRevision());
				}

				if (recordShallowSubmodules
						&& proj.getRecommendShallow() != null) {
					cfg.setBoolean("submodule"
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
				submodUrl = relativize(targetUri
			}
			cfg.setString("submodule"
			cfg.setString("submodule"
					submodUrl.toString());

			if (objectId != null) {
				DirCacheEntry dcEntry = new DirCacheEntry(path);
				dcEntry.setObjectId(objectId);
				dcEntry.setFileMode(FileMode.GITLINK);
				builder.add(dcEntry);

				for (CopyFile copyfile : proj.getCopyFiles()) {
					RemoteFile rf = callback.readFileWithMode(url
							proj.getRevision()
					objectId = inserter.insert(Constants.OBJ_BLOB
							rf.getContents());
					dcEntry = new DirCacheEntry(copyfile.dest);
					dcEntry.setObjectId(objectId);
					dcEntry.setFileMode(rf.getFileMode());
					builder.add(dcEntry);
				}
				for (LinkFile linkfile : proj.getLinkFiles()) {
					String link;
						link = FileUtils.relativizeGitPath(
								linkfile.dest.substring(0
										linkfile.dest.lastIndexOf('/'))
					} else {
					}

					objectId = inserter.insert(Constants.OBJ_BLOB
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
		ObjectId objectId = inserter.insert(Constants.OBJ_BLOB
				content.getBytes(UTF_8));
		dcEntry.setObjectId(objectId);
		dcEntry.setFileMode(FileMode.REGULAR_FILE);
		builder.add(dcEntry);

		if (recordSubmoduleLabels) {
			DirCacheEntry dcEntryAttr = new DirCacheEntry(
					Constants.DOT_GIT_ATTRIBUTES);
			ObjectId attrId = inserter.insert(Constants.OBJ_BLOB
					attributes.toString().getBytes(UTF_8));
			dcEntryAttr.setObjectId(attrId);
			dcEntryAttr.setFileMode(FileMode.REGULAR_FILE);
			builder.add(dcEntryAttr);
		}

		builder.finish();
	}

