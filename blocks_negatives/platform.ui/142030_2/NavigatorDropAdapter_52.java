    /**
     * Performs a resource move
     */
    private IStatus performResourceMove(IResource[] sources) {
        MultiStatus problems = new MultiStatus(PlatformUI.PLUGIN_ID, 1,
                ResourceNavigatorMessages.DropAdapter_problemsMoving, null);
        mergeStatus(problems, validateTarget(getCurrentTarget(),
                getCurrentTransfer()));
