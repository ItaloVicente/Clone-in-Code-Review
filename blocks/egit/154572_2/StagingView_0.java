	private void updateTitle(boolean force) {
		ViewerLabel label = titleLabels.computeIfAbsent(
				titleNode.getRepository().getDirectory(),
				r -> new ViewerLabel(null, null));
		if (force) {
			if (label.getImage() != null) {
				form.setImage(label.getImage());
			}
			if (!StringUtils.isEmptyOrNull(label.getText())) {
				form.setText(label.getText());
			}
		}
		titleLabelProvider.updateLabel(label, titleNode);
		if (label.hasNewImage()) {
			form.setImage(label.getImage());
		}
		if (label.hasNewText()) {
			form.setText(label.getText());
		}
	}

