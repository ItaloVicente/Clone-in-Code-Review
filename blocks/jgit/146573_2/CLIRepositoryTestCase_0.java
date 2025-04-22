	protected String shellQuote(String s) {
		return "'" + s.replace("'"
	}

	protected String shellQuote(File f) {
		return "'" + f.getPath().replace("'"
	}

