				IResourceDelta delta = event.getDelta();
				IResourceDelta[] children = event.getDelta().getAffectedChildren();
				IResource resource = children[0].getResource();
				Repository repo = RepositoryMapping.getMapping(resource).getRepository();
				GitResourceDeltaVisitor visitor = new GitResourceDeltaVisitor(repo);
				try {
					delta.accept(visitor);
					handleResourceChange(subscriber, repo,
							visitor.getFileResourcesToUpdate());
				} catch (CoreException e) {
					Activator.logError(e.getMessage(), e);
