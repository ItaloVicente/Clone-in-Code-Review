
		displayArea = new Composite(aParent, SWT.NONE);
		layout = new StackLayout();
		displayArea.setLayout(layout);
		createEmptyArea(displayArea);

		super.createPartControl(displayArea);
