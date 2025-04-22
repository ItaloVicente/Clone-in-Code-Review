				removeSelection();
			}
		};
		removeButton.addSelectionListener(listener);
		removeButton.setEnabled(enableComposite);
		removeButton.setFont(font);
		setButtonLayoutData(removeButton);

	}

	private void createMaxIterationsField(Composite composite) {
		maxItersField = new IntegerFieldEditor(
