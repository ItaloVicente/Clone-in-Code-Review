public interface IWorkbenchWindow extends IPageService, IRunnableContext,
		IServiceLocator, IShellProvider {
    /**
     * Closes this workbench window.
     * <p>
     * If the window has an open editor with unsaved content, the user will be
     * given the opportunity to save it.
     * </p>
     *
     * @return <code>true</code> if the window was successfully closed, and
     *         <code>false</code> if it is still open
     */
    boolean close();
