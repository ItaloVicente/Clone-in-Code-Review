		textEditor.addListener(SWT.Modify, e -> {
			Point textSize = textEditor.computeSize(SWT.DEFAULT,
					SWT.DEFAULT);
			textSize.x += textSize.y; // Add extra space for new
			Point parentSize = textEditorParent.getSize();
			textEditor.setBounds(2, inset, Math.min(textSize.x,
					parentSize.x - 4), parentSize.y - 2 * inset);
			textEditorParent.redraw();
