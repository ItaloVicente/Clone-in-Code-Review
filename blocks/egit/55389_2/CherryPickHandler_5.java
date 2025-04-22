			Button cbStrategy = new Button(parent, SWT.CHECK);
			cbStrategy.setText(UIText.MergeDialog_cbStrategy_Text);
			cbStrategy.setToolTipText(UIText.MergeDialog_cbStrategy_Tooltip);
			final Group strategyGroup = new Group(parent, SWT.NONE);
			strategyGroup
					.setText(UIText.MergeTargetSelectionDialog_MergeStrategy);
			GridDataFactory.fillDefaults().grab(true, false)
					.applyTo(strategyGroup);
			strategyGroup.setLayout(new GridLayout(1, false));
			strategyGroup.setVisible(false);

			cbStrategy.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (((Button) e.widget).getSelection()) {
						strategyGroup.setVisible(true);
					} else {
						strategyGroup.setVisible(false);
					}
				}
			});

			helper.createContents(strategyGroup);
			helper.load();

