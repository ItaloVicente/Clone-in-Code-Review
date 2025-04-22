	void addPages();

	boolean canFinish();

	void createPageControls(Composite pageContainer);

	void dispose();

	IWizardContainer getContainer();

	Image getDefaultPageImage();

	IDialogSettings getDialogSettings();

	IWizardPage getNextPage(IWizardPage page);

	IWizardPage getPage(String pageName);

	int getPageCount();

	IWizardPage[] getPages();

	IWizardPage getPreviousPage(IWizardPage page);

	IWizardPage getStartingPage();

	RGB getTitleBarColor();

	String getWindowTitle();

