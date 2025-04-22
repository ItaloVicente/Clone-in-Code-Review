		textEditor.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event e) {
				Point textSize = textEditor.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				Point parentSize = textEditorParent.getSize();
				textEditor.setBounds(2, 1, Math.min(textSize.x, parentSize.x - 4), parentSize.y - 2);
				textEditorParent.redraw();
			}
