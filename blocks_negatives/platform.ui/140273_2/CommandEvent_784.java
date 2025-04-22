    /**
     * Whether the attributes of the command have changed.  These are name and
     * value pairs representing properties of the command.
     */
    private final boolean attributeValuesByNameChanged;

    /**
     * Whether the category identifier has changed.
     */
    private final boolean categoryIdChanged;

    /**
     * The command that has changed; this value is never <code>null</code>.
     */
    private final ICommand command;

    /**
     * Whether the defined state of the command has changed.
     */
    private final boolean definedChanged;

    /**
     * Whether the description of the command has changed.
     */
    private final boolean descriptionChanged;

    /**
     * Whether the command has either gained or lost a handler.
     */
    private final boolean handledChanged;

    /**
     * Whether the key bindings for the command have changed.
     */
    private final boolean keySequenceBindingsChanged;

    /**
     * Whether the name of the command has changed.
     */
    private final boolean nameChanged;

    /**
     * The map of attributes before the change.  This is a map of attribute name
     * (strings) to values (any object).
     */
    private Map previousAttributeValuesByName;

    /**
     * Creates a new instance of this class.
     *
     * @param command
     *            the instance of the interface that changed.
     * @param attributeValuesByNameChanged
     *            true, iff the attributeValuesByName property changed.
     * @param categoryIdChanged
     *            true, iff the categoryId property changed.
     * @param definedChanged
     *            true, iff the defined property changed.
     * @param descriptionChanged
     *            true, iff the description property changed.
     * @param handledChanged
     *            true, iff the handled property changed.
     * @param keySequenceBindingsChanged
     *            true, iff the keySequenceBindings property changed.
     * @param nameChanged
     *            true, iff the name property changed.
     * @param previousAttributeValuesByName
     *            the map of previous attribute values by name. This map may be
     *            empty. If this map is not empty, it's collection of keys must
     *            only contain instances of <code>String</code>. This map
     *            must be <code>null</code> if attributeValuesByNameChanged is
     *            <code>false</code> and must not be null if
     *            attributeValuesByNameChanged is <code>true</code>.
     */
    public CommandEvent(ICommand command, boolean attributeValuesByNameChanged,
            boolean categoryIdChanged, boolean definedChanged,
            boolean descriptionChanged, boolean handledChanged,
            boolean keySequenceBindingsChanged, boolean nameChanged,
            Map previousAttributeValuesByName) {
        if (command == null) {
