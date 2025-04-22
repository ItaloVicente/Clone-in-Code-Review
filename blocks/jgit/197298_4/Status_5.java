			outw.flush();
		}
	}

	protected void printHint(String hint
								boolean lbAfter) throws IOException {
		if (!porcelain) {
			if (spaceBefore) {
			}
			outw.print(CLIText.formatLine(hint));
			if (lbAfter) {
			}
