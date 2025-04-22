					event.getDelta().accept(new IResourceDeltaVisitor() {
						public boolean visit(IResourceDelta delta)
								throws CoreException {
							final IResource resource = delta.getResource();
							if (Team.isIgnoredHint(resource))
								return false;

							if (delta.getKind() == IResourceDelta.CHANGED
									&& (delta.getFlags() & INTERESTING_CHANGES) == 0)
								return true;

							if (resource.getType() != IResource.FILE)
								return true;

							final RepositoryMapping mapping = RepositoryMapping
									.getMapping(resource);
							if (mapping == null
									|| mapping.getRepository() != repository)
								return true;

							if (resource.getName().equals(GITIGNORE_NAME)) {
								gitIgnoreChanged[0] = true;
								return false;
							}

							String repoRelativePath = mapping
									.getRepoRelativePath(resource);
							filesToUpdate.add(repoRelativePath);
							fileResourcesToUpdate.add((IFile) resource);

							return true;
						}
					});
