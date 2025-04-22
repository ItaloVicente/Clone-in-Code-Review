        implements IIdentifierListener, IActivityManagerListener {

    private IIdentifier identifier = null;

    /**
     * Creates a new contribution item from the given action. The id of the
     * action is used as the id of the item.
     *
     * @param action
     *            the action
     */
    public PluginActionContributionItem(PluginAction action) {
        super(action);
    }

    /**
     * Hook the activity and identifier listener (if necessary);
     *
     * @since 3.1
     */
    private void hookListeners() {
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .addActivityManagerListener(this);
        IIdentifier id = getIdentifier();
        if (id != null) {
