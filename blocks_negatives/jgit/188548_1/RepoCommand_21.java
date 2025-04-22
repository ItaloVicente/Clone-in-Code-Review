			try (RevWalk rw = new RevWalk(repo)) {
				Config cfg = new Config();
				StringBuilder attributes = new StringBuilder();
				for (RepoProject proj : renamedProjects) {
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
							String field = proj.getRevision().startsWith(
							cfg.setString("submodule", name, field, //$NON-NLS-1$
									proj.getRevision());
						}

						if (recordShallowSubmodules && proj.getRecommendShallow() != null) {
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
