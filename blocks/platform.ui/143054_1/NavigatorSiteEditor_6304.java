		textEditor.addListener(SWT.Modify, e -> {
			Point textSize = textEditor.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			textSize.x += textSize.y; // Add extra space for new characters.
			Point parentSize = textEditorParent.getSize();
			textEditor.setBounds(2, 1, Math.min(textSize.x, parentSize.x - 4), parentSize.y - 2);
			textEditorParent.redraw();
