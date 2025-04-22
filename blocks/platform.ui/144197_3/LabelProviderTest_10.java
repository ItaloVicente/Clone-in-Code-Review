			@Override
			protected void addListenerTo(RenamableItem next) {
				next.addListener(listener);
			}

			@Override
			protected void removeListenerFrom(RenamableItem next) {
				next.removeListener(listener);
			}
		});
		list.setInput(setOfRenamables);

		selectedRenamable = ViewerProperties.singleSelection(RenamableItem.class).observe(list);

		Composite buttonBar = new Composite(shell, SWT.NONE);
		addButton = new Button(buttonBar, SWT.PUSH);
		addButton.setText("Add"); //$NON-NLS-1$
		addButton.addSelectionListener(buttonSelectionListener);
		removeButton = new Button(buttonBar, SWT.PUSH);
		removeButton.addSelectionListener(buttonSelectionListener);
		removeButton.setText("Remove"); //$NON-NLS-1$
		renameButton = new Button(buttonBar, SWT.PUSH);
		renameButton.addSelectionListener(buttonSelectionListener);
		renameButton.setText("Rename"); //$NON-NLS-1$

		selectedRenamable
				.addValueChangeListener(event -> {
					boolean shouldEnable = selectedRenamable.getValue() != null;
					removeButton.setEnabled(shouldEnable);
					renameButton.setEnabled(shouldEnable);
				});
		removeButton.setEnabled(false);
		renameButton.setEnabled(false);

		GridLayoutFactory.fillDefaults().generateLayout(buttonBar);
