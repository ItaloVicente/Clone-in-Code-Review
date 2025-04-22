 * safely reference the controls created.  When the part is closed
 * these controls will be disposed as part of an SWT composite.  This
 * occurs before the <code>IWorkbenchPart.dispose</code> method is called.
 * If there is a need to free SWT resources the part should define a dispose
 * listener for its own control and free those resources from the dispose
 * listener.  If the part invokes any method on the disposed SWT controls
 * after this point an <code>SWTError</code> will be thrown.
