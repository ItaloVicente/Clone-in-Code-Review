				if (repo.isBare()) {
					bareProjects = new ArrayList<>();
					if (author == null)
						author = new PersonIdent(repo);
					if (callback == null)
						callback = new DefaultRemoteReader();
					for (RepoProject proj : parser.getFilteredProjects()) {
						addSubmoduleBare(proj.getUrl()
								proj.getPath()
								proj.getRevision()
								proj.getCopyFiles()
								proj.getLinkFiles()
								proj.getGroups()
								proj.getRecommendShallow());
					}
					DirCache index = DirCache.newInCore();
					DirCacheBuilder builder = index.builder();
					ObjectInserter inserter = repo.newObjectInserter();
					try (RevWalk rw = new RevWalk(repo)) {
						Config cfg = new Config();
						StringBuilder attributes = new StringBuilder();
						for (RepoProject bareProj : bareProjects) {
							String path = bareProj.getPath();
							String nameUri = bareProj.getName();
							ObjectId objectId;
							if (ObjectId.isId(bareProj.getRevision())) {
								objectId = ObjectId
										.fromString(bareProj.getRevision());
							} else {
								objectId = callback.sha1(nameUri
										bareProj.getRevision());
								if (objectId == null) {
									if (ignoreRemoteFailures) {
										continue;
									}
									throw new RemoteUnavailableException(
											nameUri);
								}
								if (recordRemoteBranch) {
									cfg.setString("submodule"
											"branch"
											bareProj.getRevision());
								}

								if (recordShallowSubmodules && bareProj
										.getRecommendShallow() != null) {
									cfg.setBoolean("submodule"
											"shallow"
											true);
								}
							}
							if (recordSubmoduleLabels) {
								StringBuilder rec = new StringBuilder();
								rec.append(path);
								for (String group : bareProj.getGroups()) {
									rec.append(group);
								}
								attributes.append(rec.toString());
