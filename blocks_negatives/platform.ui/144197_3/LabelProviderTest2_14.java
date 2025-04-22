			});
			list.setInput(listOfRenamables);

			selectedRenamable = ViewerProperties.singleSelection(RenamableItem.class).observe(list);

			Composite buttonBar = new Composite(shell, SWT.NONE);
				addButton = new Button(buttonBar, SWT.PUSH);
				addButton.addSelectionListener(buttonSelectionListener);
				removeButton = new Button(buttonBar, SWT.PUSH);
				removeButton.addSelectionListener(buttonSelectionListener);
				renameButton = new Button(buttonBar, SWT.PUSH);
				renameButton.addSelectionListener(buttonSelectionListener);

				selectedRenamable.addValueChangeListener(event -> {
					boolean shouldEnable = selectedRenamable.getValue() != null;
					removeButton.setEnabled(shouldEnable);
					renameButton.setEnabled(shouldEnable);
				});
				removeButton.setEnabled(false);
				renameButton.setEnabled(false);

				GridLayoutFactory.fillDefaults().generateLayout(buttonBar);
