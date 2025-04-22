				removeSelectedVariables();
			}
		});
		removeButton.setFont(font);
		setButtonLayoutData(removeButton);
		updateEnabledState();
	}

	protected void initializeDialogUnits(Control control) {
		GC gc = new GC(control);
		gc.setFont(control.getFont());
		fontMetrics = gc.getFontMetrics();
		gc.dispose();
	}

	private void initTemporaryState() {
		tempPathVariables.clear();
