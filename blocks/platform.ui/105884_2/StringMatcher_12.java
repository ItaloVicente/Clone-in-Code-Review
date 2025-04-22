			}
			return false;
		}
		return true;
	}

	protected int textPosIn(String text, int start, int end, String p) {

		int plen = p.length();
		int max = end - plen;

		if (!fIgnoreCase) {
			int i = text.indexOf(p, start);
			if (i == -1 || i > max) {
