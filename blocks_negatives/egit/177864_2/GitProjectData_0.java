					File gitCandidate = location.toFile().getParentFile();
					File git = new FileRepositoryBuilder()
							.addCeilingDirectory(gitCandidate)
							.findGitDir(gitCandidate).getGitDir();
					if (git == null) {
						return false;
					}
					GitProjectData data = get(resource.getProject());
					if (data == null) {
						return false;
					}
					RepositoryMapping m = RepositoryMapping
							.create(resource.getParent(), git);
					try {
						Repository r = RepositoryCache.getInstance()
								.lookupRepository(git);
						if (m != null && r != null && !r.isBare()
								&& gitCandidate.equals(r.getWorkTree())) {
							if (data.map(m)) {
								data.mappings.put(m.getContainerPath(), m);
								modified.add(data);
							}
						}
					} catch (IOException e) {
						Activator.logError(e.getMessage(), e);
					}
