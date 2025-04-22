    	contents = new Composite(parent,SWT.NONE);
    	GridLayout layout = new GridLayout();
    	layout.marginBottom=0;
    	layout.marginTop=0;
    	contents.setLayout(layout);
    	
    	initFilteringControl(contents);
    	
        tree = new Tree(contents, SWT.FULL_SELECTION | SWT.SINGLE
