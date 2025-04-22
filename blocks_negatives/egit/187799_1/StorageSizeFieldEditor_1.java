		String n = str.trim();
		if (n.length() == 0)
			return 0;

		int mul = 1;
		char lastChar = n.charAt(n.length() - 1);
		switch (Character.toLowerCase(lastChar)) {
		case 'g':
			mul = GB;
			break;
		case 'm':
			mul = MB;
			break;
		case 'k':
			mul = KB;
			break;
		default:
			if (Character.isDigit(lastChar)) {
				break;
			}
		}
		if (mul > 1)
			n = n.substring(0, n.length() - 1).trim();
		if (n.length() == 0)
			return 0;

