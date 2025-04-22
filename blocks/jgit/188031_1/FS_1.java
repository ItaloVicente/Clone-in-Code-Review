		if (w == null) {
			return null;
		}
		int nul = w.indexOf(0);
		if (nul <= 0) {
			return null;
		}
		w = w.substring(0
		int colon = w.indexOf(':');
		if (colon < 0) {
