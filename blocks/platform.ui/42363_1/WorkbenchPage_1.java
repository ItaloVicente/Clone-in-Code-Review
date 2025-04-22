	private PerspectiveDescriptor fixOrphanPerspective(MPerspective mperspective) {
		PerspectiveRegistry reg = (PerspectiveRegistry) PlatformUI.getWorkbench().getPerspectiveRegistry();
		String perspId = mperspective.getElementId();
		String label = mperspective.getLabel();
		String msg = "Perspective with name '" + label + "' and id '" + perspId + "' has been made into a local copy"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
		IStatus status = StatusUtil.newStatus(IStatus.WARNING, msg, null);
		StatusManager.getManager().handle(status, StatusManager.LOG);

		String newDescId = NLS.bind(WorkbenchMessages.Perspective_localCopyLabel, label);
		while (reg.findPerspectiveWithId(newDescId) != null) {
			newDescId = NLS.bind(WorkbenchMessages.Perspective_localCopyLabel, newDescId);
		}
		PerspectiveDescriptor pd = new PerspectiveDescriptor(perspId, label, null);
		PerspectiveDescriptor newDesc = reg.createPerspective(newDescId, pd);
		mperspective.setElementId(newDesc.getId());
		mperspective.setLabel(newDesc.getLabel());
		sortedPerspectives.add(newDesc);
		modelService.cloneElement(mperspective, application);
		newDesc.setHasCustomDefinition(true);
		return newDesc;
	}

