     * The <code>AbstractUIPlugin</code> implementation of this <code>Plugin</code>
     * method does nothing.  Subclasses may extend this method, but must send
     * super first.
     * <p>
     * WARNING: Plug-ins may not be started in the UI thread.
     * The <code>startup()</code> method should not assume that its code runs in
     * the UI thread, otherwise SWT thread exceptions may occur on startup.'
     * @deprecated
     * In Eclipse 3.0, <code>startup</code> has been replaced by {@link Plugin#start(BundleContext context)}.
     * Implementations of <code>startup</code> should be changed to extend
     * <code>start(BundleContext context)</code> and call <code>super.start(context)</code>
     * instead of <code>super.startup()</code>. Like <code>super.startup()</code>,
     * <code>super.stop(context)</code> must be called as the very first thing.
     * The <code>startup</code> method is called only for plug-ins which explicitly require the
     * org.eclipse.core.runtime.compatibility plug-in; in contrast,
     * the <code>start</code> method is always called.
     */
