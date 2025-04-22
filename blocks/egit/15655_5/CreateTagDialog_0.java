
		boolean existingTagSelected = existingTag != null;
		if (existingTagSelected && !overwriteButton.getSelection())
			tagMessageText.getTextWidget().setEditable(false);
		else
			tagMessageText.getTextWidget().setEditable(true);

		overwriteButton.setEnabled(existingTagSelected);
		if (!existingTagSelected)
			overwriteButton.setSelection(false);
