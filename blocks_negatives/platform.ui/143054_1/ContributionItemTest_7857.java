    /**
     * Tests that an action contribution item will display the text in a
     * created SWT button when the MODE_FORCE_TEXT mode is set.
     * This is a test for:
     * Bug 187956 [ActionSets] ActionContributionItem.MODE_FORCE_TEXT should apply to Buttons too
     */
    public void testForceModeText() {
    	Action action = new DummyAction();
    	action.setImageDescriptor(
    	AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui.tests",
    			"icons/anything.gif"));
    	ActionContributionItem item = new ActionContributionItem(action);
    	item.fill(getShell());
