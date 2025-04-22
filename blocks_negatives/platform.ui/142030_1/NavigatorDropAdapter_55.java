        return returnCode[0];
    }

    /**
     * This method is used to notify the action that some aspect of
     * the drop operation has changed.
     */
    @Override
	public boolean validateDrop(Object target, int dragOperation,
            TransferData transferType) {

        if (dragOperation != DND.DROP_NONE) {
            lastValidOperation = dragOperation;
        }
        if (FileTransfer.getInstance().isSupportedType(transferType)
                && (lastValidOperation != DND.DROP_COPY)) {
            return false;
        }
        if (super.validateDrop(target, dragOperation, transferType)) {
            return true;
        }
        return validateTarget(target, transferType).isOK();
    }

    /**
     * Ensures that the drop target meets certain criteria
     */
    private IStatus validateTarget(Object target, TransferData transferType) {
        if (!(target instanceof IResource)) {
            return info(ResourceNavigatorMessages.DropAdapter_targetMustBeResource);
        }
        IResource resource = (IResource) target;
        if (!resource.isAccessible()) {
            return error(ResourceNavigatorMessages.DropAdapter_canNotDropIntoClosedProject);
        }
        IContainer destination = getActualTarget(resource);
        if (destination.getType() == IResource.ROOT) {
            return error(ResourceNavigatorMessages.DropAdapter_resourcesCanNotBeSiblings);
        }
        String message = null;
        if (LocalSelectionTransfer.getInstance().isSupportedType(transferType)) {
            IResource[] selectedResources = getSelectedResources();

            if (selectedResources.length == 0) {
