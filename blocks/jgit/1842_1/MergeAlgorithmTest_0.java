
	public static String t(String text) {
		StringBuilder r = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			switch (c) {
			case '<':
				r.append("<<<<<<< O\n");
				break;
			case '=':
				r.append("=======\n");
				break;
			case '>':
				r.append(">>>>>>> T\n");
				break;
			default:
				r.append(c);
				r.append('\n');
			}
		}
		return r.toString();
	}

	public static RawText T(String text) {
		return new RawText(Constants.encode(t(text)));
	}

