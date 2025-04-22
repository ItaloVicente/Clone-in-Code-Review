            });
            
            
            final Button limitSize = new Button(buttonBar, SWT.CHECK);
            limitSize.setLayoutData(new GridData(GridData.FILL_BOTH));
            limitSize.setText("Limit table size to 400");
            limitSize.addSelectionListener(new SelectionAdapter() {
            	/* (non-Javadoc)
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
				 */
