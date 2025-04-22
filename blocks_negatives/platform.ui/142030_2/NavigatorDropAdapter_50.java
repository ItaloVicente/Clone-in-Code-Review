        }
        openError(status);
        return result;
    }

    /**
     * Performs a drop using the FileTransfer transfer type.
     */
    private IStatus performFileDrop(Object data) {
        MultiStatus problems = new MultiStatus(PlatformUI.PLUGIN_ID, 0,
                ResourceNavigatorMessages.DropAdapter_problemImporting, null);
        mergeStatus(problems, validateTarget(getCurrentTarget(),
                getCurrentTransfer()));

		final int currentOperation= getCurrentOperation();
        final IContainer target = getActualTarget((IResource) getCurrentTarget());
        final String[] names = (String[]) data;
        Display.getCurrent().asyncExec(() -> {
		    getShell().forceActive();
