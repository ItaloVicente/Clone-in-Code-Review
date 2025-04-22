	private Resource createResource() {
		if (saveAndRestore) {
			URI saveLocation = URI.createFileURI(getWorkbenchSaveLocation().getAbsolutePath());
			return resourceSetImpl.createResource(saveLocation);
		}
		return resourceSetImpl.createResource(URI.createURI("workbench.xmi")); //$NON-NLS-1$
	}

