    private boolean hasRun = false;

    /**
     * Constructor for MockAction
     */
    protected MockAction(String text) {
        super(text);
        TestPlugin plugin = TestPlugin.getDefault();
        setImageDescriptor(plugin.getImageDescriptor("anything.gif"));
        setToolTipText(text);
    }

    /**
     * Constructor for MockAction
     */
    protected MockAction(String text, ImageDescriptor image) {
        super(text, image);
        setToolTipText(text);
    }

    @Override
