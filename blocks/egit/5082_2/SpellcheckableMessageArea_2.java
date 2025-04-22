		final TextEditorPropertyAction showWhitespaceAction = new TextEditorPropertyAction(
				UIText.SpellcheckableMessageArea_showWhitespace,
				sourceViewer,
				AbstractTextEditor.PREFERENCE_SHOW_WHITESPACE_CHARACTERS) {

			private IPainter whitespaceCharPainter;

			@Override
			protected void toggleState(boolean checked) {
				if (checked)
					installPainter();
				else
					uninstallPainter();
			}

			private void installPainter() {
				Assert.isTrue(whitespaceCharPainter == null);
				ITextViewer v = getTextViewer();
				if (v instanceof ITextViewerExtension2) {
					whitespaceCharPainter = new WhitespaceCharacterPainter(v);
					((ITextViewerExtension2) v).addPainter(whitespaceCharPainter);
				}
			}

			private void uninstallPainter() {
				if (whitespaceCharPainter == null)
					return;
				ITextViewer v = getTextViewer();
				if (v instanceof ITextViewerExtension2)
					((ITextViewerExtension2) v)
							.removePainter(whitespaceCharPainter);
				whitespaceCharPainter.deactivate(true);
				whitespaceCharPainter = null;
			}
		};

