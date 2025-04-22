    public AboutPluginsDialog(Shell parentShell, String productName,
            Bundle[] bundles, String title, String message, String helpContextId) {
    	super(parentShell);
    	AboutPluginsPage page = new AboutPluginsPage();
    	page.setHelpContextId(helpContextId);
    	page.setBundles(bundles);
    	page.setMessage(message);
    	if (title == null && page.getProductName() != null)
            title = NLS.bind(WorkbenchMessages.AboutPluginsDialog_shellTitle, productName);
    	initializeDialog(page, title, helpContextId);
