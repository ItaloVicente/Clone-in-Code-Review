	private static EditList diff(RawText a, RawText b) {
		return new PatienceDiff().diff(RawTextComparator.DEFAULT, a, b);
	}

	private static RawText t(String text) {
		StringBuilder r = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			r.append(text.charAt(i));
			r.append('\n');
		}
		try {
			return new RawText(r.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

