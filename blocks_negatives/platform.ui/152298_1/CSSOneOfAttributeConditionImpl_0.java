		String val = getValue();
		for (StringTokenizer tok = new StringTokenizer(attr); tok.hasMoreElements();) {
			String candidate = tok.nextToken();
			if (val.equals(candidate)) {
				return true;
			}
		}
		return false;
