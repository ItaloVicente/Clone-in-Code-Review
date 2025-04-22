	 * @param next
	 *            the action that moves forward
	 * @param previous
	 *            the action that moves backward
	 */
    public static void linkCycleActionPair(IWorkbenchAction next,
            IWorkbenchAction previous) {
    }

    /**
     * Id of actions created by this action factory.
     */
    private final String actionId;

    /**
     * Optional ID for this action.
     */
    private final String commandId;

    /**
     * Creates a new workbench action factory with the given id.
     *
     * @param actionId
     *            the id of actions created by this action factory
     */
    protected ActionFactory(String actionId) {
    	this(actionId, null);
    }
