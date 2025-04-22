	private boolean validateDelete(List<? extends IResource> resources) {
		IResourceChangeDescriptionFactory factory = ResourceChangeValidator.getValidator().createDeltaFactory();
		for (IResource resource : resources) {
			if (resource instanceof IProject) {
				IProject project = (IProject) resource;
				factory.delete(project);
			}
		}
		String message;
		if (resources.size() == 1) {
			message = NLS.bind(IDEWorkbenchMessages.CloseResourceAction_warningForOne, resources.get(0).getName());
		} else {
			message = IDEWorkbenchMessages.CloseResourceAction_warningForMultiple;
		}
		return IDE.promptToConfirm(shellProvider.getShell(), IDEWorkbenchMessages.CloseResourceAction_confirm, message,
				factory.getDelta(), getModelProviderIds(), false /* no need to syncExec */);
	}

