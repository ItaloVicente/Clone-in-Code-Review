			final ScrolledComposite scroller = new ScrolledComposite(topContainer, SWT.V_SCROLL | SWT.H_SCROLL);
			data = new GridData(GridData.FILL_BOTH);
			data.widthHint = minWidth;
			scroller.setLayoutData(data);

			final Composite textComposite = new Composite(scroller, SWT.NONE);
			textComposite.setBackground(background);

			layout = new GridLayout();
			textComposite.setLayout(layout);

			text = new StyledText(textComposite, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);

