			try {
				int nLines = document.getNumberOfLines();
				if (nLines > 0) {
					int lastLineLength = document.getLineLength(nLines - 1);
					if (lastLineLength == 0) {
						int lineStart = document.getLineOffset(nLines - 1);
						document.set(document.get(0, lineStart - 1));
					}
				}
			} catch (BadLocationException e) {
			}
