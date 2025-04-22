	private void initFilteringControl(Composite parent) {
		GridLayout layout;
		Composite filterComposite = new Composite(parent, SWT.NONE);
    	layout = new GridLayout();
    	layout.marginBottom=0;
    	layout.marginTop=0;
    	layout.numColumns=3;
    	filterComposite.setLayout(layout);
    	Label label = new Label(filterComposite,SWT.NONE);
    	label.setText("Filter:"); //$NON-NLS-1$
    	final Text filterText = doCreateFilterText(filterComposite);
    	filterText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				textFilter = ((Text)e.getSource()).getText();
				refresh();
			}
		});
    	filterText.getAccessible().addAccessibleListener(
    			new AccessibleAdapter() {
					public void getName(AccessibleEvent e) {
						String filterTextString = filterText.getText();
						if (filterTextString.length() == 0 || filterTextString.equals("")) { //$NON-NLS-1$
							e.result = ""; //$NON-NLS-1$
						} else {
							e.result = NLS.bind(WorkbenchMessages.FilteredTree_AccessibleListenerFiltered,
									new String[] {filterTextString, String.valueOf(getFilteredItemsCount()) });
						}
					}
					
					private int getFilteredItemsCount() {
						int total = 0;
						TreeItem[] items =getPropertiesTreeControl().getItems(); 
						for (int i = 0; i < items.length; i++) {
							total += itemCount(items[i]);
						}
						return total;
					}					
    				
					private int itemCount(TreeItem treeItem) {
						int count = 1;
						TreeItem[] children = treeItem.getItems();
						for (int i = 0; i < children.length; i++) {
							count += itemCount(children[i]);
						}
						return count;
					}
    			
    			});
    	filterText.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				Display display= filterText.getDisplay();
				display.asyncExec(new Runnable() {
					public void run() {
						if (!filterText.isDisposed()) {
							if ("".equals(filterText.getText().trim())) { //$NON-NLS-1$
								filterText.selectAll();
							}
						}
					}
				});
				return;
			}

			public void focusLost(FocusEvent e) {
					return;
			}
    	});
		filterText.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				boolean hasItems = tree.getItemCount() > 0;
				if (hasItems && e.keyCode == SWT.ARROW_DOWN) {
					tree.setFocus();
					return;
				}
			}
		});
    	Button clearBtn = new Button(filterComposite, SWT.NONE);
    	clearBtn.setText("Clear"); //$NON-NLS-1$
    	clearBtn.addMouseListener(new MouseAdapter() {
    		public void mouseUp(MouseEvent e) {
    			textFilter = ""; //$NON-NLS-1$
    			filterText.setText(""); //$NON-NLS-1$
    			refresh();
    		}
		});
	}
	
	private Text doCreateFilterText(Composite parent) {
		return new Text(parent,SWT.SINGLE|SWT.BORDER);
	}
	
