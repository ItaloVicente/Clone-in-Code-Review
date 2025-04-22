    private IStructuredContentProvider fContentProvider;

    private ILabelProvider fLabelProvider;

    private Object fInput;

    private TableViewer fTableViewer;

    private boolean fAddCancelButton = true;

    private int widthInChars = 55;

    private int heightInChars = 15;

    /**
     * Create a new instance of the receiver with parent shell of parent.
     * @param parent
     */
    public ListDialog(Shell parent) {
        super(parent);
    }

    /**
     * @param input The input for the list.
     */
    public void setInput(Object input) {
        fInput = input;
    }

    /**
     * @param sp The content provider for the list.
     */
    public void setContentProvider(IStructuredContentProvider sp) {
        fContentProvider = sp;
    }

    /**
     * @param lp The labelProvider for the list.
     */
    public void setLabelProvider(ILabelProvider lp) {
        fLabelProvider = lp;
    }

    /**
     *@param addCancelButton if <code>true</code> there will be a cancel
     * button.
     */
    public void setAddCancelButton(boolean addCancelButton) {
        fAddCancelButton = addCancelButton;
    }

    /**
     * @return the TableViewer for the receiver.
     */
    public TableViewer getTableViewer() {
        return fTableViewer;
    }

    @Override
