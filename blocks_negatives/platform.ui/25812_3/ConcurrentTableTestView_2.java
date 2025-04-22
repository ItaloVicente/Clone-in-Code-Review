            });
            
            Button resetCountButton = new Button(buttonBar, SWT.PUSH);
            resetCountButton.setLayoutData(new GridData(GridData.FILL_BOTH));
            resetCountButton.setText("Reset comparison count");
	        resetCountButton.addSelectionListener(new SelectionAdapter() {
		        /* (non-Javadoc)
	             * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	             */
	            public void widgetSelected(SelectionEvent e) {
	                comparator.comparisons = 0;
	                scheduleComparisonUpdate();
	            } 
	        });
	        
	        Button testButton = new Button(buttonBar, SWT.PUSH);
	        testButton.setLayoutData(new GridData(GridData.FILL_BOTH));
	        testButton.setText("add 100000 elements");
	        testButton.addSelectionListener(new SelectionAdapter() {
		        /* (non-Javadoc)
	             * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	             */
	            public void widgetSelected(SelectionEvent e) {
	                addRandomElements(100000);
	            } 
	        });
	        
	        Button removeButton = new Button(buttonBar, SWT.PUSH);
	        removeButton.setLayoutData(new GridData(GridData.FILL_BOTH));
	        removeButton.setText("remove all");
	        removeButton.addSelectionListener(new SelectionAdapter() {
		        /* (non-Javadoc)
	             * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	             */
	            public void widgetSelected(SelectionEvent e) {
	                clear();
	            } 
	        });

        }
    }
    
    
    /**
     * 
     * @since 3.1
     */
    protected void scheduleComparisonUpdate() {
        updateCountRunnable.schedule(100);
    }



    public void addRandomElements(int amount) {
        
        ArrayList tempList = new ArrayList();

        for (int counter = 0; counter < amount; counter++) {
            tempList.add("" + rand.nextLong() + " " + counter );
        }
        
        model.addAll(tempList);
    }
    
    public void clear() {
        model.clear();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPart#setFocus()
     */
    public void setFocus() {

    }
