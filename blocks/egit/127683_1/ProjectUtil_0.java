			try {
				if (projectFile.exists()) {
					p.refreshLocal(IResource.DEPTH_INFINITE,
							progress.newChild(1));
				} else if (delete) {
					p.delete(false, true, progress.newChild(1));
				} else {
					closeMissingProject(p, projectFile, progress.newChild(1));
				}
			} catch (@SuppressWarnings("restriction")
			ResourceException e) {
				Activator.logWarning(e.getMessage(), e);
			}
