    private TableViewer table;
    private boolean enableSlowComparisons = false;
    private TestComparator comparator = new TestComparator() {
        
        /* (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(Object arg0, Object arg1) {

        	if (enableSlowComparisons) {
	            
	            long timestamp = System.currentTimeMillis();
	            while (System.currentTimeMillis() < timestamp + delay) {
	            }
        	}
            
            int result = super.compare(arg0, arg1);
            
            scheduleComparisonUpdate();
            
            return result;
        }
    };
    private DeferredContentProvider contentProvider;
    
    private WorkbenchJob updateCountRunnable = new WorkbenchJob("") {
        
        /* (non-Javadoc)
         * @see org.eclipse.ui.progress.UIJob#runInUIThread(org.eclipse.core.runtime.IProgressMonitor)
         */
        public IStatus runInUIThread(IProgressMonitor monitor) {
            updateCount.setText("Comparison count = " + comparator.comparisons);
            return Status.OK_STATUS;
        }
    };
    
    private Label updateCount;
    private SetModel model = new SetModel();
    private Random rand = new Random();
    private Button slowComparisons;
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite temp) {
        Composite parent = new Composite(temp, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        
        parent.setLayout(layout);
        
        {
	        table = new TableViewer(parent, SWT.VIRTUAL);
	        contentProvider = new DeferredContentProvider(comparator);
	        table.setContentProvider(contentProvider);
	        
	        GridData data = new GridData(GridData.FILL_BOTH);
	        table.getControl().setLayoutData(data);
	        table.setInput(model);
        }
        
        Composite buttonBar = new Composite(parent, SWT.NONE);
        buttonBar.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout buttonBarLayout = new GridLayout();
        buttonBarLayout.numColumns = 1;
        buttonBar.setLayout(buttonBarLayout);
        {

            updateCount = new Label(buttonBar, SWT.NONE);
            updateCount.setLayoutData(new GridData(GridData.FILL_BOTH));

            slowComparisons = new Button(buttonBar, SWT.CHECK);
            slowComparisons.setLayoutData(new GridData(GridData.FILL_BOTH));
            slowComparisons.setText("Slow comparisons");
            slowComparisons.setSelection(enableSlowComparisons);
            slowComparisons.addSelectionListener(new SelectionAdapter() {
            	/* (non-Javadoc)
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
				 */
