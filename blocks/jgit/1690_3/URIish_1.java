	private String n2e(String s) {
		if (s == null)
			return "";
		else
			return s;
	}

	private String cleanLeadingSlashes(String p
		if (p.length() >= 3
				&& p.charAt(0) == '/'
				&& p.charAt(2) == ':'
				&& (p.charAt(1) >= 'A' && p.charAt(1) <= 'Z' || p.charAt(1) >= 'a'
						&& p.charAt(1) <= 'z'))
			return p.substring(1);
		else if (s != null && p.length() >= 2 && p.charAt(0) == '/'
				&& p.charAt(1) == '~')
			return p.substring(1);
		else
			return p;
	}

