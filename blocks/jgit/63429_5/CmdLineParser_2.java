	private void printToErrorWriter(String error) {
		if (cmd == null) {
			System.err.println(error);
		} else {
			try {
				cmd.getErrorWriter().println(error);
			} catch (IOException e1) {
				System.err.println(error);
			}
		}
	}

