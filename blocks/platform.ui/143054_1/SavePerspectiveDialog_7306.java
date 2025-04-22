		if (persp != null) {
			perspName = persp.getLabel();
			text.setText(perspName);
		}

		updateButtons();
	}

	public void setInitialSelection(IPerspectiveDescriptor selectedElement) {
		initialSelection = selectedElement;
	}

	private void updateButtons() {
		if (okButton != null) {
			String label = text.getText();
			okButton.setEnabled(perspReg.validateLabel(label));
		}
	}
