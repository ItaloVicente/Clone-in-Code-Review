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
    	final Text text = new Text(filterComposite,SWT.SINGLE|SWT.BORDER);
    	text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				textFilter = ((Text)e.getSource()).getText();
				refresh();
			}
		});
    	Button clearBtn = new Button(filterComposite, SWT.NONE);
    	clearBtn.setText("Clear"); //$NON-NLS-1$
    	clearBtn.addMouseListener(new MouseAdapter() {
    		public void mouseUp(MouseEvent e) {
    			textFilter = ""; //$NON-NLS-1$
    			text.setText(""); //$NON-NLS-1$
    			refresh();
    		}
		});
	}
    
