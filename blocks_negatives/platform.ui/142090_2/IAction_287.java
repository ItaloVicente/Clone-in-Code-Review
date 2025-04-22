    /**
     * Property name of an action's handler. Some actions delegate some or all
     * of their behaviour or state to another object. In this case, if the
     * object to which behaviour has been delegated changes, then a property
     * change event should be sent with this name.
     *
     * This is used to support backward compatibility of actions within the
     * commands framework.
     *
     * @since 3.1
     */
