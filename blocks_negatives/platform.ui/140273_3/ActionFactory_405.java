    /**
     * Interface for a workbench action.
     */
    public interface IWorkbenchAction extends IAction {
        /**
         * Disposes of this action. Once disposed, this action cannot be used.
         * This operation has no effect if the action has already been
         * disposed.
         */
        void dispose();
    }

    private static class WorkbenchCommandAction extends CommandAction implements
			IWorkbenchAction {
