		String newText = existingTag.getName();
		String oldText = tagNameText.getText();
		if (!oldText.equals(newText)) {
			int oldCaretPos = tagNameText.getSelection().y;

			tagNameText.setText(newText);
			if (oldCaretPos == oldText.length()
					|| oldCaretPos > newText.length()) {
				tagNameText.setSelection(newText.length());
			} else {
				tagNameText.setSelection(oldCaretPos);
			}
		}
		if (commitCombo != null) {
