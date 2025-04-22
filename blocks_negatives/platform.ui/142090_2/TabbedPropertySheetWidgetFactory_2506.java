    extends FormToolkit {

    /**
     * private constructor.
     */
    public TabbedPropertySheetWidgetFactory() {
        super(Display.getCurrent());
    }

    /**
     * Creates the tab folder as a part of the form.
     *
     * @param parent
     *            the composite parent.
     * @param style
     *            the tab folder style.
     * @return the tab folder
     */
    public CTabFolder createTabFolder(Composite parent, int style) {
        CTabFolder tabFolder = new CTabFolder(parent, style);
        return tabFolder;
    }

    /**
     * Creates the tab item as a part of the tab folder.
     *
     * @param tabFolder
     *            the parent.
     * @param style
     *            the tab folder style.
     * @return the tab item.
     */
    public CTabItem createTabItem(CTabFolder tabFolder, int style) {
        CTabItem tabItem = new CTabItem(tabFolder, style);
        return tabItem;
    }

    /**
     * Creates the list as a part of the form.
     *
     * @param parent
     *            the composite parent.
     * @param style
     *            the list style.
     * @return the list.
     */
    public List createList(Composite parent, int style) {
        List list = new org.eclipse.swt.widgets.List(parent, style);
        return list;
    }

    @Override
