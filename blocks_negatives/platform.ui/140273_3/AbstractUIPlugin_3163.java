        super.startup();
    }

    /**
     * The <code>AbstractUIPlugin</code> implementation of this <code>Plugin</code>
     * method does nothing. Subclasses may extend this method, but must send
     * super first.
     * @deprecated
     * In Eclipse 3.0, <code>shutdown</code> has been replaced by {@link Plugin#stop(BundleContext context)}.
     * Implementations of <code>shutdown</code> should be changed to extend
     * <code>stop(BundleContext context)</code> and call <code>super.stop(context)</code>
     * instead of <code>super.shutdown()</code>. Unlike <code>super.shutdown()</code>,
     * <code>super.stop(context)</code> must be called as the very <b>last</b> thing rather
     * than as the very first thing. The <code>shutdown</code> method is called
     * only for plug-ins which explicitly require the
     * org.eclipse.core.runtime.compatibility plug-in;
     * in contrast, the <code>stop</code> method is always called.
     */
    @Deprecated
