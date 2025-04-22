		String n = str.trim();
		if (n.length() == 0) {
			return defaultValue;
		}
		long mul = 1;
		switch (StringUtils.toLowerCase(n.charAt(n.length() - 1))) {
		case 'g':
			mul = Config.GiB;
			break;
		case 'm':
			mul = Config.MiB;
			break;
		case 'k':
			mul = Config.KiB;
			break;
		}
		if (mul > 1) {
			n = n.substring(0, n.length() - 1).trim();
		}
		if (n.length() == 0) {
			return defaultValue;
		}
