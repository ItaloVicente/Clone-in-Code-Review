		resourceChangeListener = new IResourceChangeListener() {

			public void resourceChanged(IResourceChangeEvent event) {
				for (IResourceDelta delta : event.getDelta().getAffectedChildren()) {
					RepositoryMapping repo = RepositoryMapping.getMapping(delta.getResource());
					if (repo != null)
						update(subscriber, repo);
				}
			}
		};
