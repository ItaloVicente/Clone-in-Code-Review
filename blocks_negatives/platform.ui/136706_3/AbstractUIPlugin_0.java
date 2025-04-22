    /**
	 * The {@link #AbstractUIPlugin(IPluginDescriptor)} constructor was called
	 * only for plug-ins which explicitly require the
	 * org.eclipse.core.runtime.compatibility plug-in.
	 *
	 * It is not called anymore as Eclipse 4.6 removed this plug-in.
	 */
    @Deprecated
	public AbstractUIPlugin(IPluginDescriptor descriptor) {
        super(descriptor);
    }

