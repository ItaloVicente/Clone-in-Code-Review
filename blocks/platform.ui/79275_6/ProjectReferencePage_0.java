		project = Adapters.adapt(getElement(), IProject.class);
		if (project == null) {
			IResource resource = Adapters.adapt(getElement(), IResource.class);
			Assert.isNotNull(resource, "unable to adapt element to a project"); //$NON-NLS-1$
			project = resource.getProject();
		}
