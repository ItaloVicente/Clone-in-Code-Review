
		private void handleProjectOpenEvent(final IResourceChangeEvent event) {
			final IResourceDelta delta = event.getDelta();
			if (delta != null) {
				final boolean isProjectOpenCloseEvent = delta.getKind() == IResourceDelta.CHANGED
						&& (delta.getFlags() & IResourceDelta.OPEN) == 0;
				if (isProjectOpenCloseEvent) {
					for (IResourceDelta change : delta.getAffectedChildren()) {
						IProject project = (IProject) change.getResource()
								.getAdapter(IProject.class);

						if (project.isOpen()) {
							try {
								final RepositoryMapping rm = RepositoryMapping
										.getMapping(project);
								if ((rm != null)) {
									rm.getRepository().getConfig().load();
								}
							} catch (IOException e) {
								e.printStackTrace();
							} catch (ConfigInvalidException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
