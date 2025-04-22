	public void sendError(final String what) {
		if (refs == null) {
			if (advertiseError == null)
				advertiseError = new StringBuilder();
			advertiseError.append(what).append('\n');
		} else {
		}
	}

	private void fatalError(String msg) {
		if (errOut != null) {
			try {
				errOut.write(Constants.encode(msg));
				errOut.flush();
			} catch (IOException e) {
			}
		} else {
			sendError(msg);
		}
	}
