 * A local working set manager can be used to manage a set of
 * working sets independently from the working sets managed by
 * the global working set manager.  A local working set manager
 * can be saved and restored using the methods <code>saveState</code>
 * and <code>restoreState</code>.  A new local working set manager can be created
 * using {@link org.eclipse.ui.IWorkbench#createLocalWorkingSetManager()}.
 * Clients of local working set managers are responsible for calling
 * {@link IWorkingSetManager#dispose()} when the working sets it manages
 * are no longer needed.
