	private void touchGitResources(IProject project, IProgressMonitor monitor) {
		final SubMonitor progress = SubMonitor.convert(monitor, 1);
		try {
			project.accept(new IResourceProxyVisitor() {
				@Override
				public boolean visit(IResourceProxy resource)
						throws CoreException {
					int type = resource.getType();
					if ((type == IResource.FILE || type == IResource.FOLDER)
							&& Constants.DOT_GIT.equals(resource.getName())) {
						progress.setWorkRemaining(2);
						resource.requestResource().touch(progress.newChild(1));
						return false;
					}
					return true;
				}
			}, IResource.NONE);
		} catch (CoreException e) {
			Activator.error(e.getMessage(), e);
		}
	}

