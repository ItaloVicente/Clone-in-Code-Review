     * Creates an abstract UI plug-in runtime object for the given plug-in
     * descriptor.
     * <p>
     * Note that instances of plug-in runtime classes are automatically created
     * by the platform in the course of plug-in activation.
     * <p>
     *
     * @param descriptor the plug-in descriptor
     * @see Plugin#Plugin(org.eclipse.core.runtime.IPluginDescriptor descriptor)
     * @deprecated
     * In Eclipse 3.0 this constructor has been replaced by
     * {@link #AbstractUIPlugin()}. Implementations of
     * <code>MyPlugin(IPluginDescriptor descriptor)</code> should be changed to
     * <code>MyPlugin()</code> and call <code>super()</code> instead of
     * <code>super(descriptor)</code>.
     * The <code>MyPlugin(IPluginDescriptor descriptor)</code> constructor is
     * called only for plug-ins which explicitly require the
     * org.eclipse.core.runtime.compatibility plug-in (or, as in this case,
     * subclasses which might).
     */
