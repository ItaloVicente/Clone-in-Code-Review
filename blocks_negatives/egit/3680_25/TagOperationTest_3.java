
		project.accept(new IResourceVisitor() {

			public boolean visit(IResource resource) throws CoreException {
				if (resource instanceof IFile) {
					try {
						repository1
								.track(EFS.getStore(resource.getLocationURI())
										.toLocalFile(0, null));
					} catch (Exception e) {
						throw new CoreException(Activator.error(e.getMessage(),
								e));
					}
				}
				return true;
			}
		});

