 * so that it can keep its enablement state up to date. Ordinarily, the
 * window's references to these listeners will be dropped automatically when
 * the window closes. However, if the client needs to get rid of an action
 * while the window is still open, the client must call
 * {@link IWorkbenchAction#dispose dispose} to give the action an
 * opportunity to deregister its listeners and to perform any other cleanup.
