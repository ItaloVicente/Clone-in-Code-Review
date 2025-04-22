     * Runs the given operation in the UI thread using the given runnable context.
     * The given scheduling rule, if any, will be acquired for the duration of the operation.
     * If the rule is not available when this method is called, a progress dialog will be
     * displayed that gives users control over the background processes that may
     * be blocking the runnable from proceeding.
     * <p>
     * This method can act as a wrapper for uses of <tt>IRunnableContext</tt>
     * where the <tt>fork</tt> parameter was <tt>false</tt>.
     * <p>
     * Note: Running long operations in the UI thread is generally not
     * recommended. This can result in the UI becoming unresponsive for
     * the duration of the operation. Where possible, <tt>busyCursorWhile</tt>
     * should be used instead.
     * </p>
     * <p>
     * Modal dialogs should also be avoided in the runnable as there will already
     * be a modal progress dialog open when this operation runs.
     * </p>
     * @see org.eclipse.jface.dialogs.Dialog
     * @see org.eclipse.swt.SWT#APPLICATION_MODAL
     *
     * @param context The runnable context to run the operation in
     * @param runnable The operation to run
     * @param rule A scheduling rule, or <code>null</code>
     * @throws InvocationTargetException wraps any exception or error which occurs
     *  while running the runnable
     * @throws InterruptedException propagated by the context if the runnable
     *  acknowledges cancelation by throwing this exception.
     *
     */
