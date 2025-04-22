
	@Override
	protected void createCustomArea(Composite parent) {
		Composite main = new Composite(parent, SWT.NONE);
		main.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(main);
		Group g = new Group(main, SWT.NONE);
		g.setText(UIText.MergeTargetSelectionDialog_MergeTypeGroup);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(g);
		g.setLayout(new GridLayout(1, false));

		Button commit = new Button(g, SWT.RADIO);
		commit.setSelection(true);
		commit.setText(UIText.MergeTargetSelectionDialog_MergeTypeCommitButton);
		commit.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (((Button) event.widget).getSelection())
					mergeSquash = false;
			}
		});

		Button squash = new Button(g, SWT.RADIO);
		squash.setText(UIText.MergeTargetSelectionDialog_MergeTypeSquashButton);
		squash.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (((Button) event.widget).getSelection())
					mergeSquash = true;
			}
		});
	}

	public boolean isMergeSquash() {
		return mergeSquash;
	}
