			} else {
				msg = getErrorMessage();
			}
		}

		if (msg != null) { // error
			showErrorMessage(msg);
			return false;
		}

		if(doCheckState()) { // OK!
			clearErrorMessage();
			return true;
		}
		msg = getErrorMessage(); // subclass might have changed it in the #doCheckState()
		if (msg != null) {
			showErrorMessage(msg);
		}
		return false;
	}

	private File getFile(File startingDirectory) {

		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN | SWT.SHEET);
		if (startingDirectory != null) {
