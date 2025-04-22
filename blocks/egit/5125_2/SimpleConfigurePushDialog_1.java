		ExpandableComposite pushUriArea = new ExpandableComposite(main,
				ExpandableComposite.TREE_NODE
						| ExpandableComposite.CLIENT_INDENT);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(pushUriArea);
		pushUriArea.setExpanded(!config.getPushURIs().isEmpty());
		pushUriArea.addExpansionListener(new ExpansionAdapter() {

			public void expansionStateChanged(ExpansionEvent e) {
				main.layout(true, true);
			}
		});
		pushUriArea.setText(UIText.SimpleConfigurePushDialog_PushUrisLabel);
		final Composite pushUriDetails = new Composite(pushUriArea, SWT.NONE);
		pushUriArea.setClient(pushUriDetails);
		pushUriDetails.setLayout(new GridLayout(2, false));
