	private static int scanToken(String header, int from) {
		int length = header.length();
		int i = from;
		while (i < length) {
			char c = header.charAt(i);
			switch (c) {
			case '!':
			case '#':
			case '$':
			case '%':
			case '&':
			case '\'':
			case '*':
			case '+':
			case '-':
			case '.':
			case '^':
			case '_':
			case '`':
			case '|':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				i++;
				break;
			default:
				if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
					i++;
					break;
				}
				return i;
			}
		}
		return i;
	}

