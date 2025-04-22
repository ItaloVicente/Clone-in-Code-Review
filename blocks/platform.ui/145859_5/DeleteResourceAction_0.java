	private boolean validateDelete(List<? extends IResource> resources) {
		IResourceChangeDescriptionFactory factory = ResourceChangeValidator.getValidator().createDeltaFactory();
		for (IResource resource : resources) {
			if (resource instanceof IProject) {
				IProject project = (IProject) resource;
				factory.delete(project);
			}
		}
		String message = IDEWorkbenchMessages.DeleteResourceAction_warning;
		return IDE.promptToConfirm(shellProvider.getShell(), IDEWorkbenchMessages.DeleteResourceAction_confirm, message,
				factory.getDelta(), getModelProviderIds(), false /* no need to syncExec */);
	}

