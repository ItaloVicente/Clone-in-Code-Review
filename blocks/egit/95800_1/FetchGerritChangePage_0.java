	private void doPaste(Text text) {
		Clipboard clipboard = new Clipboard(text.getDisplay());
		try {
			String clipText = (String) clipboard
					.getContents(TextTransfer.getInstance());
			if (clipText != null) {
				String toInsert = determineChangeFromString(clipText.trim());
				if (toInsert != null) {
					clipboard.setContents(new Object[] { toInsert },
							new Transfer[] { TextTransfer.getInstance() });
					try {
						text.paste();
					} finally {
						clipboard.setContents(new Object[] { clipText },
								new Transfer[] { TextTransfer.getInstance() });
					}
				} else {
					text.paste();
				}
			}
		} finally {
			clipboard.dispose();
		}
	}

