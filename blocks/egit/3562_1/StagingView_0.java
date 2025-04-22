		IResourceChangeListener resourceChangeListener = new IResourceChangeListener() {
			public void resourceChanged(IResourceChangeEvent event) {
				final Collection<String> resourcesToUpdate = new ArrayList<String>();
				final Collection<String> removedResources = new ArrayList<String>();

				try {
					event.getDelta().accept(new IResourceDeltaVisitor() {
						public boolean visit(IResourceDelta delta) throws CoreException {
							if (delta.getKind() == IResourceDelta.CHANGED
									&& (delta.getFlags() & INTERESTING_CHANGES) == 0) {
								return true;
							}

							final IResource resource = delta.getResource();

							if (resource.getType() != IResource.FILE)
								return true;

							final RepositoryMapping mapping = RepositoryMapping
									.getMapping(resource);
							if (mapping == null || mapping.getRepository() != currentRepository) {
								return true;
							}

							if (Team.isIgnoredHint(resource))
								return false;

							String repoRelativePath = mapping.getRepoRelativePath(resource);
							resourcesToUpdate.add(repoRelativePath);
							if ((delta.getKind() == IResourceDelta.REMOVED))
								removedResources.add(repoRelativePath);

							return true;
						}
					});
				} catch (CoreException e) {
					MessageDialog.openError(getSite().getShell(),
							UIText.StagingView_exceptionTitle,
							UIText.StagingView_exceptionMessage);
				}

				if (!resourcesToUpdate.isEmpty() || !removedResources.isEmpty()) {

					final IndexDiff indexDiff;
					try {
						WorkingTreeIterator iterator = IteratorService.createInitialIterator(currentRepository);
						indexDiff = new IndexDiff(currentRepository, Constants.HEAD, iterator);
						indexDiff.setFilter(PathFilterGroup.createFromStrings(resourcesToUpdate));
						indexDiff.diff();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}

					form.getDisplay().asyncExec(new Runnable() {
						public void run() {
							if (form.isDisposed())
								return;

							unstagedTableViewer.setInput(new Object[] { currentRepository,
									indexDiff, removedResources });
							stagedTableViewer
									.setInput(new Object[] { currentRepository, indexDiff, removedResources });

							updateSectionText();
						}
					});
				}
			}
		};
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener,
				IResourceChangeEvent.POST_CHANGE);

