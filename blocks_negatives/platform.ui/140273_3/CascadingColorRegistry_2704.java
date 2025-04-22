    /**
     * Disposes of all allocated resources.
     */
    public void dispose() {
        parent.removeListener(listener);
        PlatformUI.getWorkbench().getDisplay().asyncExec(displayRunnable);
    }
