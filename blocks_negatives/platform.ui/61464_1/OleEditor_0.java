    /**
     * Keep track of whether we have an active client so we do not
     * deactivate multiple times
     */
    private boolean clientActive = false;

    /**
     * Keep track of whether we have activated OLE or not as some applications
     * will only allow single activations.
     */
    private boolean oleActivated = false;

    private IPartListener partListener = new IPartListener() {
        @Override
		public void partActivated(IWorkbenchPart part) {
            activateClient(part);
        }

        @Override
		public void partBroughtToTop(IWorkbenchPart part) {
        }

        @Override
		public void partClosed(IWorkbenchPart part) {
        }

        @Override
		public void partOpened(IWorkbenchPart part) {
        }

        @Override
		public void partDeactivated(IWorkbenchPart part) {
            deactivateClient(part);
        }
    };

