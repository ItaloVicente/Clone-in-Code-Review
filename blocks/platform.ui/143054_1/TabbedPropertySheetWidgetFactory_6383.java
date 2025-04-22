	extends FormToolkit {

	public TabbedPropertySheetWidgetFactory() {
		super(Display.getCurrent());
	}

	public CTabFolder createTabFolder(Composite parent, int style) {
		CTabFolder tabFolder = new CTabFolder(parent, style);
		return tabFolder;
	}

	public CTabItem createTabItem(CTabFolder tabFolder, int style) {
		CTabItem tabItem = new CTabItem(tabFolder, style);
		return tabItem;
	}

	public List createList(Composite parent, int style) {
		List list = new org.eclipse.swt.widgets.List(parent, style);
		return list;
	}

	@Override
