						if (!doAutoShare())
							return false;
						if (delta.getKind() == IResourceDelta.CHANGED
								&& (delta.getFlags() & INTERESTING_CHANGES) == 0)
							return true;
						final IResource resource = delta.getResource();
						if (!resource.exists() || !resource.isAccessible() ||
								resource.isLinked(IResource.CHECK_ANCESTORS))
							return false;
						if (resource.getType() != IResource.PROJECT)
							return true;
						if (RepositoryMapping.getMapping(resource) != null)
							return false;
						final IProject project = (IProject) resource;
						RepositoryProvider provider = RepositoryProvider
								.getProvider(project);
						if (provider != null)
							return false;
						RepositoryFinder f = new RepositoryFinder(project);
						Collection<RepositoryMapping> mappings = f.find(new NullProgressMonitor());
						try {
							if (mappings.size() == 1) {
								RepositoryMapping m = mappings.iterator()
										.next();
								final File repositoryDir = m
										.getGitDirAbsolutePath().toFile();

								projects.put(project, repositoryDir);

								Activator.getDefault().getRepositoryUtil()
										.addConfiguredRepository(repositoryDir);
							}
						} catch (IllegalArgumentException e) {
							logError(CoreText.Activator_AutoSharingFailed, e);
						}
						return false;
