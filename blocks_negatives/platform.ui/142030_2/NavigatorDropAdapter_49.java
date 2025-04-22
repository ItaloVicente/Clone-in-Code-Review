        alwaysOverwrite = false;
        if (getCurrentTarget() == null || data == null) {
            return false;
        }
        boolean result = false;
        IStatus status = null;
        IResource[] resources = null;
        TransferData currentTransfer = getCurrentTransfer();
        if (LocalSelectionTransfer.getInstance().isSupportedType(
                currentTransfer)) {
            resources = getSelectedResources();
        } else if (ResourceTransfer.getInstance().isSupportedType(
                currentTransfer)) {
            resources = (IResource[]) data;
        } else if (FileTransfer.getInstance().isSupportedType(currentTransfer)) {
            status = performFileDrop(data);
            result = status.isOK();
        } else {
            result = NavigatorDropAdapter.super.performDrop(data);
        }
        if (resources != null && resources.length > 0) {
            if (getCurrentOperation() == DND.DROP_COPY) {
