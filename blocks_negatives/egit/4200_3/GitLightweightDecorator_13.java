	/**
	 * Callback for IResourceChangeListener events
	 *
	 * Schedules a refresh of the changed resource
	 *
	 * If the preference for computing deep dirty states has been set we walk
	 * the ancestor tree of the changed resource and update all parents as well.
	 *
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		final long currentTime = System.currentTimeMillis();
		final Set<IResource> resourcesToUpdate = new HashSet<IResource>();

			event.getDelta().accept(new IResourceDeltaVisitor() {
				public boolean visit(IResourceDelta delta) throws CoreException {

					if (delta.getKind() == IResourceDelta.CHANGED
							&& (delta.getFlags() & INTERESTING_CHANGES) == 0) {
						return true;
					}

					final IResource resource = delta.getResource();

					final RepositoryMapping mapping = RepositoryMapping
							.getMapping(resource);
					if (mapping == null) {
						return true;
					}

					if (resource.getType() == IResource.ROOT) {
						return true;
					}

					if (resource.getType() == IResource.PROJECT) {
						if (!resource.isAccessible())
							return false;
					}

					if (currentTime - resource.getLocalTimeStamp() > 10000)
						return false;

					if (Team.isIgnoredHint(resource))
						return false;

					if (Constants.GITIGNORE_FILENAME.equals(resource.getName())) {
						IContainer parent = resource.getParent();
						if (parent.exists())
							resourcesToUpdate.addAll(Arrays.asList(parent
									.members()));
						else
							return false;
					} else {
						resourcesToUpdate.add(resource);
					}

					if (delta.getKind() == IResourceDelta.CHANGED
							&& (delta.getFlags() & IResourceDelta.OPEN) > 1)
					else
						return true;
				}
			}, true /* includePhantoms */);
		} catch (final CoreException e) {
			handleException(null, e);
		}

		if (resourcesToUpdate.isEmpty())
			return;

		final IPreferenceStore store = Activator.getDefault()
				.getPreferenceStore();
		if (store.getBoolean(UIPreferences.DECORATOR_RECOMPUTE_ANCESTORS)) {
			final IResource[] changedResources = resourcesToUpdate
					.toArray(new IResource[resourcesToUpdate.size()]);
			for (IResource current : changedResources) {
				while (current.getType() != IResource.ROOT) {
					current = current.getParent();
					resourcesToUpdate.add(current);
				}
			}
		}

		postLabelEvent(resourcesToUpdate.toArray());
	}

	public void onIndexChanged(IndexChangedEvent e) {
		postLabelEvent();
	}

	public void onRefsChanged(RefsChangedEvent e) {
		postLabelEvent();
	}

	/**
	 * Callback for RepositoryChangeListener events, as well as
	 * RepositoryListener events via repositoryChanged()
	 *
	 * @see org.eclipse.egit.core.project.RepositoryChangeListener#repositoryChanged(org.eclipse.egit.core.project.RepositoryMapping)
	 */
	public void repositoryChanged(RepositoryMapping mapping) {
