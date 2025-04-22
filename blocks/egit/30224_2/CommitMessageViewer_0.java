	private void revealFirstDiff() {
		if (currentDiffs.isEmpty())
			return;

		String lineToSelect = currentDiffs.get(0).getPath();
		int linesNmbr = getDocument().getNumberOfLines();
		for (int i = 1; i < linesNmbr; i++) {
			try {
				IRegion lineInfo = getDocument().getLineInformation(i);
				String line = getDocument().get(lineInfo.getOffset(),
						lineInfo.getLength());
				if (line.contains(lineToSelect)) {
					getTextWidget().setCaretOffset(lineInfo.getOffset());
					revealRange(lineInfo.getOffset(), lineInfo.getLength());
					return;
				}
			} catch (BadLocationException e) {
			}
		}
	}

