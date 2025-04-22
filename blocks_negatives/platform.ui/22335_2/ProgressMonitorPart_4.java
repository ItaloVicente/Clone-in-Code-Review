        }
        return out.toString();
    }

    /**
     * Creates the progress monitor's UI parts and layouts them
     * according to the given layout. If the layout is <code>null</code>
     * the part's default layout is used.
     * @param layout The layout for the receiver.
     * @param progressIndicatorHeight The suggested height of the indicator
     */
    protected void initialize(Layout layout, int progressIndicatorHeight) {
        if (layout == null) {
            GridLayout l = new GridLayout();
            l.marginWidth = 0;
            l.marginHeight = 0;
            layout = l;
        }
        int numColumns = 1;
        if (fHasStopButton)
        	numColumns++;
        setLayout(layout);
        
        if (layout instanceof GridLayout)
        	((GridLayout)layout).numColumns = numColumns;

        fLabel = new Label(this, SWT.LEFT);
        fLabel.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false, numColumns, 1));
		
        if (progressIndicatorHeight == SWT.DEFAULT) {
            GC gc = new GC(fLabel);
            FontMetrics fm = gc.getFontMetrics();
            gc.dispose();
            progressIndicatorHeight = fm.getHeight();
        }

        fProgressIndicator = new ProgressIndicator(this);
        GridData gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.grabExcessVerticalSpace = false;
    	gd.verticalAlignment = GridData.CENTER;
    	gd.heightHint = progressIndicatorHeight;
        fProgressIndicator.setLayoutData(gd);

        if (fHasStopButton) {
        	fToolBar = new ToolBar(this, SWT.FLAT);

        	gd = new GridData();
            gd.grabExcessHorizontalSpace = false;
            gd.grabExcessVerticalSpace = false;
        	gd.verticalAlignment = GridData.CENTER;
        	fToolBar.setLayoutData(gd);
        	fStopButton = new ToolItem(fToolBar, SWT.PUSH);
        	fStopButton.addSelectionListener(new SelectionAdapter() {
        		@Override
