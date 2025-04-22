	IWorkbenchPart getPart(boolean restore);

	String getTitle();

	Image getTitleImage();

	String getTitleToolTip();

	String getId();

	void addPropertyListener(IPropertyListener listener);

	void removePropertyListener(IPropertyListener listener);

	IWorkbenchPage getPage();

