	private void closeToken() {
		if (token != null) {
			token.close();
			token = null;
		}
	}

