	private void updateTitle() {
		titleLabelProvider.updateLabel(titleLabel, titleNode);
		if (titleLabel.hasNewImage()) {
			form.setImage(titleLabel.getImage());
		}
		if (titleLabel.hasNewText()) {
			form.setText(titleLabel.getText());
		}
	}

