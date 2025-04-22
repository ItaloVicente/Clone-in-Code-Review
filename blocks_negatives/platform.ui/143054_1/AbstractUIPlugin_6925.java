    @Deprecated
	public AbstractUIPlugin(IPluginDescriptor descriptor) {
        super(descriptor);
    }

    /**
     * Creates an abstract UI plug-in runtime object.
     * <p>
     * Plug-in runtime classes are <code>BundleActivators</code> and so must
     * have an default constructor.  This method is called by the runtime when
     * the associated bundle is being activated.
     * <p>
     * For more details, see <code>Plugin</code>'s default constructor.
     *
     * @see Plugin#Plugin()
     * @since 3.0
     */
    public AbstractUIPlugin() {
        super();
    }

    /**
     * Returns a new image registry for this plugin-in.  The registry will be
     * used to manage images which are frequently used by the plugin-in.
     * <p>
     * The default implementation of this method creates an empty registry.
     * Subclasses may override this method if needed.
     * </p>
     *
     * @return ImageRegistry the resulting registry.
     * @see #getImageRegistry
     */
    protected ImageRegistry createImageRegistry() {

    	if(Display.getCurrent() != null) {
