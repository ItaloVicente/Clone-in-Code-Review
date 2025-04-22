			public void resourceChanged(IResourceChangeEvent event) {
				if (event.getDelta() == null)
					return;

				for (IResourceDelta delta : event.getDelta().getAffectedChildren()) {
					RepositoryMapping repo = RepositoryMapping.getMapping(delta.getResource());
					if (repo != null)
						update(subscriber, repo);
				}
