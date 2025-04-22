    /**
     * The attribute name for the checked property of the wrapped action. This
     * indicates whether the action should be displayed with as a checked check
     * box.
     */

    /**
     * The attribute name for the enabled property of the wrapped action.
     */

    /**
     * <p>
     * The name of the attribute indicating whether the wrapped instance of
     * <code>RetargetAction</code> has a handler.
     * </p>
     */
    private static final String ATTRIBUTE_HANDLED = IHandlerAttributes.ATTRIBUTE_HANDLED;

    /**
     * The attribute name for the identifier of the wrapped action. This is the
     * action identifier, and not the command identifier.
     */

    /**
     * The attribute name for the visual style of the wrapped action. The style
     * can be things like a pull-down menu, a check box, a radio button or a
     * push button.
     */

    /**
     * The wrapped action. This value is never <code>null</code>.
     */
    private final IAction action;

    /**
     * The map of attributes values. The keys are <code>String</code> values
     * of the attribute names (given above). The values can be any type of
     * <code>Object</code>.
     *
     * This map is always null if there are no IHandlerListeners registered.
     *
     */
    private Map attributeValuesByName;

    /**
     * The property change listener hooked on to the action. This is initialized
     * when the first listener is attached to this handler, and is removed when
     * the handler is disposed or the last listener is removed.
     */
    private IPropertyChangeListener propertyChangeListener;

    /**
     * Creates a new instance of this class given an instance of
     * <code>IAction</code>.
     *
     * @param action
     *            the action. Must not be <code>null</code>.
     */
