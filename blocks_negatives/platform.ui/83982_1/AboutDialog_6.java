        	final ScrolledComposite scroller = new ScrolledComposite(topContainer,
    				SWT.V_SCROLL | SWT.H_SCROLL);
        	data = new GridData(GridData.FILL_BOTH);
        	data.widthHint = minWidth;
    		scroller.setLayoutData(data);

    		final Composite textComposite = new Composite(scroller, SWT.NONE);
    		textComposite.setBackground(background);

    		layout = new GridLayout();
    		textComposite.setLayout(layout);

    		text = new StyledText(textComposite, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);


            text.setFont(parent.getFont());
            text.setText(item.getText());
            text.setCursor(null);
            text.setBackground(background);
            text.setForeground(foreground);

            aboutTextManager = new AboutTextManager(text);
            aboutTextManager.setItem(item);

            createTextMenu();

    		GridData gd = new GridData();
    		gd.verticalAlignment = GridData.BEGINNING;
    		gd.horizontalAlignment = GridData.FILL;
    		gd.grabExcessHorizontalSpace = true;
    		text.setLayoutData(gd);

    		scroller.getHorizontalBar().setIncrement(20);
    		scroller.getVerticalBar().setIncrement(20);

    		textComposite.addControlListener(new ControlAdapter() {
    			@Override
