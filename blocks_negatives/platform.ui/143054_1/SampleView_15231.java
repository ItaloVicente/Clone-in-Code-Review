    extends ViewPart
    implements ITabbedPropertySheetPageContributor {

    private ListViewer viewer;

    private Group grp1;

    /**
     * The constructor.
     */
    public SampleView() {
    }

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    public void createPartControl(Composite parent) {
        viewer = new ListViewer(parent, SWT.SINGLE);

        grp1 = new Group(parent, SWT.NONE);
        RowLayout rowLayout = new RowLayout();
        grp1.setLayout(rowLayout);

        Button btn = new Button(grp1, SWT.PUSH);

        ArrayList ctlList = new ArrayList();
        ButtonElement btnEl = new ButtonElement(btn, "Button");//$NON-NLS-1$
        ctlList.add(btnEl);

        viewer.setContentProvider(new ArrayContentProvider());
        viewer.setLabelProvider(new WorkbenchLabelProvider());
        viewer.setInput(ctlList);
        getSite().setSelectionProvider(viewer);

    }

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus() {
        viewer.getControl().setFocus();
    }

    /**
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
     */
    public Object getAdapter(Class adapter) {
        if (adapter == IPropertySheetPage.class)
            return new TabbedPropertySheetPage(this);
        return super.getAdapter(adapter);
    }

    /**
     * @see org.eclipse.ui.IWorkbenchPart#dispose()
     */
    public void dispose() {
        super.dispose();
    }

    public String getContributorId() {
        return getSite().getId();
    }
