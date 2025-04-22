        IWorkbenchPreferencePage, IExecutableExtension {

    /**
     * The name to use for the activities.  Ie: "Capabilities".
     */

    /**
     * The parameter to use if you want the page to show the allow button. Must
     * be true or false.
     */
    
    /**
     * The string to use for the message at the top of the preference page.
     */
    
    /**
     * The name to use for the activity categories.  Ie: "Roles".
     */
    
    /**
     * The label to be used for the prompt button. Ie: "&Prompt when enabling capabilities".
     */    

    /**
     * The tooltip to be used for the prompt button. Ie: "Prompt when a feature is first used that requires enablement of capabilities".
     */    
    
    private class AdvancedDialog extends TrayDialog {


    	
        ActivityEnabler enabler;
        /**
         * @param parentShell
         */
        protected AdvancedDialog(Shell parentShell) {
            super(parentShell);
