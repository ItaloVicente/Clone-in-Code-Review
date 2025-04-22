	public static void update(IResourceChangeEvent event) {
		final Set<GitProjectData> modified = new HashSet<>();
		try {
			event.getDelta().accept(new IResourceDeltaVisitor() {
				@Override
				public boolean visit(IResourceDelta delta)
						throws CoreException {
					IResource resource = delta.getResource();
					int type = resource.getType();
					if (type == IResource.ROOT) {
						return true;
					} else if (type == IResource.PROJECT) {
						return (delta.getKind() & (IResourceDelta.ADDED
								| IResourceDelta.CHANGED)) != 0
								&& ResourceUtil.isSharedWithGit(resource);
					}
					if ((delta.getKind() & (IResourceDelta.ADDED
							| IResourceDelta.CHANGED)) == 0
							|| resource.isLinked()) {
						return false;
					}
					IPath location = resource.getLocation();
					if (location == null) {
						return false;
					}
					if (!Constants.DOT_GIT.equals(resource.getName())) {
						return type == IResource.FOLDER;
					}
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
						Repository r = Activator.getDefault()
								.getRepositoryCache().lookupRepository(git);
						if (m != null && r != null
								&& gitCandidate.equals(r.getWorkTree())) {
							data.mappings.put(m.getContainerPath(), m);
							data.map(m);
							modified.add(data);
						}
					} catch (IOException e) {
						Activator.logError(e.getMessage(), e);
					}
					return false;
				}
			});
		} catch (CoreException e) {
			Activator.logError(e.getMessage(), e);
		} finally {
			for (GitProjectData data : modified) {
				try {
					data.store();
				} catch (CoreException e) {
					Activator.logError(e.getMessage(), e);
				}
			}
		}
	}

