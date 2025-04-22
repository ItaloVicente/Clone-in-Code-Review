    /**
     * Preference page, or <code>null</code> if not yet loaded.
     */
    private IPreferencePage page;

    /**
     * The list of subnodes (immediate children) of this node (element type:
     * <code>IPreferenceNode</code>).
     */
    private List<IPreferenceNode> subNodes;

    /**
     * Name of a class that implements <code>IPreferencePage</code>, or
     * <code>null</code> if none.
     */
    private String classname;

    /**
     * The id of this node.
     */
    private String id;

    /**
     * Text label for this node. Note that this field is only used prior to the
     * creation of the preference page.
     */
    private String label;

    /**
     * Image descriptor for this node, or <code>null</code> if none.
     */
    private ImageDescriptor imageDescriptor;

    /**
     * Cached image, or <code>null</code> if none.
     */
    private Image image;

    /**
     * Creates a new preference node with the given id. The new node has no
     * subnodes.
     *
     * @param id
     *            the node id
     */
    public PreferenceNode(String id) {
        Assert.isNotNull(id);
        this.id = id;
    }

    /**
     * Creates a preference node with the given id, label, and image, and
     * lazily-loaded preference page. The preference node assumes (sole)
     * responsibility for disposing of the image; this will happen when the node
     * is disposed.
     *
     * @param id
     *            the node id
     * @param label
     *            the label used to display the node in the preference dialog's
     *            tree
     * @param image
     *            the image displayed left of the label in the preference
     *            dialog's tree, or <code>null</code> if none
     * @param className
     *            the class name of the preference page; this class must
     *            implement <code>IPreferencePage</code>
     */
    public PreferenceNode(String id, String label, ImageDescriptor image,
            String className) {
        this(id);
        this.imageDescriptor = image;
        Assert.isNotNull(label);
        this.label = label;
        this.classname = className;
    }

    /**
     * Creates a preference node with the given id and preference page. The
     * title of the preference page is used for the node label. The node will
     * not have an image.
     *
     * @param id
     *            the node id
     * @param preferencePage
     *            the preference page
     */
    public PreferenceNode(String id, IPreferencePage preferencePage) {
        this(id);
        Assert.isNotNull(preferencePage);
        page = preferencePage;
    }

    @Override
