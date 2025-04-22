	/**
	 * Paste text from the clipboard into the {@code text} field. If possible,
	 * determine the patch set number automatically. If only a change number can
	 * be determined, return {@code true} to indicate to the caller that a
	 * content assist might be helpful.
	 *
	 * @param text
	 *            {@link Text} field to paste into
	 * @return whether invoking a content assist might be helpful to the user
	 */
	private boolean doPaste(Text text) {
		Clipboard clipboard = new Clipboard(text.getDisplay());
		try {
			String clipText = (String) clipboard
					.getContents(TextTransfer.getInstance());
			if (clipText != null) {
				Change input = determineChangeFromString(
						clipText.trim());
				if (input != null) {
					String toInsert = input.getChangeNumber().toString();
					boolean replacesEverything = text.getText().trim().isEmpty()
							|| text.getSelectionText().equals(text.getText());
					boolean openContentAssist = false;
					if (input.getPatchSetNumber() != null) {
						if (replacesEverything) {
							toInsert = input.getRefName();
						} else {
							toInsert = toInsert + '/'
									+ input.getPatchSetNumber();
						}
					} else {
						openContentAssist = replacesEverything;
					}
					clipboard.setContents(new Object[] { toInsert },
							new Transfer[] { TextTransfer.getInstance() });
					try {
						text.paste();
						refTextEdited = false;
					} finally {
						clipboard.setContents(new Object[] { clipText },
								new Transfer[] { TextTransfer.getInstance() });
					}
					return openContentAssist;
				} else {
					text.paste();
				}
			}
		} finally {
			clipboard.dispose();
		}
		return false;
	}

	private void storeLastUsedUri(String uri) {
		settings.put(lastUriKey, uri.trim());
	}

	private void selectLastUsedUri() {
		String lastUri = settings.get(lastUriKey);
		if (lastUri != null) {
			int i = uriCombo.indexOf(lastUri);
			if (i != -1) {
				uriCombo.select(i);
				return;
			}
		}
		uriCombo.select(0);
	}

