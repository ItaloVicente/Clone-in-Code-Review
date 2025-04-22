
			final Group recurseGroup = new Group(panel, SWT.NULL);
			recurseGroup.setLayoutData(
					new GridData(SWT.FILL, SWT.FILL, true, false));
			recurseGroup.setText(UIText.RefSpecPage_recurseSubmodulesGroup);
			recurseGroup.setLayout(new GridLayout());
			recurseSubmodulesYesButton = new Button(recurseGroup, SWT.RADIO);
			recurseSubmodulesYesButton
					.setText(UIText.RefSpecPage_recurseSubmodulesYes);
			recurseSubmodulesNoButton = new Button(recurseGroup, SWT.RADIO);
			recurseSubmodulesNoButton
					.setText(UIText.RefSpecPage_recurseSubmodulesNo);
			recurseSubmodulesOnDemandButton = new Button(recurseGroup,
					SWT.RADIO);
			recurseSubmodulesOnDemandButton
					.setText(UIText.RefSpecPage_recurseSubmodulesOnDemand);
