			textEditorParent.addListener(SWT.Paint, new Listener() {
				@Override
				public void handleEvent(Event e) {
					Point textSize = textEditor.getSize();
					Point parentSize = textEditorParent.getSize();
					e.gc.drawRectangle(0, 0, Math.min(textSize.x + 4,
							parentSize.x - 1), parentSize.y - 1);
				}
