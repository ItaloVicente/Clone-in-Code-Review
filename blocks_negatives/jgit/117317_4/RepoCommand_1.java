				for (RepoProject proj : parser.getFilteredProjects()) {
					addSubmodule(proj.getUrl(),
							proj.getPath(),
							proj.getRevision(),
							proj.getCopyFiles(),
							proj.getLinkFiles(),
							proj.getGroups(),
							proj.getRecommendShallow());
				}
			} catch (GitAPIException | IOException e) {
				throw new ManifestErrorException(e);
			}
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
			}
		}

		if (repo.isBare()) {
			DirCache index = DirCache.newInCore();
			DirCacheBuilder builder = index.builder();
			ObjectInserter inserter = repo.newObjectInserter();
			try (RevWalk rw = new RevWalk(repo)) {
				Config cfg = new Config();
				StringBuilder attributes = new StringBuilder();
				for (RepoProject proj : bareProjects) {
					String path = proj.getPath();
					String nameUri = proj.getName();
					ObjectId objectId;
					if (ObjectId.isId(proj.getRevision())) {
						objectId = ObjectId.fromString(proj.getRevision());
					} else {
						objectId = callback.sha1(nameUri, proj.getRevision());
						if (objectId == null) {
							if (ignoreRemoteFailures) {
								continue;
