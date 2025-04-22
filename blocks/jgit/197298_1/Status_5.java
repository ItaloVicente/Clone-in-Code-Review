			outw.flush();
		}
	}

	protected void printHint(String hint
								boolean lbAfter) throws IOException {
		if (!porcelain) {
			if (spaceBefore) {
				outw.print(" ");
			}
			outw.print(CLIText.formatLine(hint));
			if (lbAfter) {
				outw.println("");
			}
