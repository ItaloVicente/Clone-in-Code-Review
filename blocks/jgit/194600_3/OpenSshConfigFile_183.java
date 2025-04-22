	public static int timeSpec(String value) {
		if (value == null) {
			return -1;
		}
		try {
			int length = value.length();
			int i = 0;
			int seconds = 0;
			boolean valueSeen = false;
			while (i < length) {
				char ch = value.charAt(i);
				if (Character.isWhitespace(ch)) {
					i++;
					continue;
				}
				if (ch == '+') {
					i++;
				}
				int val = 0;
				int j = i;
				while (j < length) {
					ch = value.charAt(j++);
					if (ch >= '0' && ch <= '9') {
						val = Math.addExact(Math.multiplyExact(val
								ch - '0');
					} else {
						j--;
						break;
					}
				}
				if (i == j) {
					return -1;
				}
				i = j;
				int multiplier = 1;
				if (i < length) {
					ch = value.charAt(i++);
					switch (ch) {
					case 's':
					case 'S':
						break;
					case 'm':
					case 'M':
						multiplier = 60;
						break;
					case 'h':
					case 'H':
						multiplier = 3600;
						break;
					case 'd':
					case 'D':
						multiplier = 24 * 3600;
						break;
					case 'w':
					case 'W':
						multiplier = 7 * 24 * 3600;
						break;
					default:
						if (Character.isWhitespace(ch)) {
							break;
						}
						return -1;
					}
				}
				seconds = Math.addExact(seconds
						Math.multiplyExact(val
				valueSeen = true;
			}
			return valueSeen ? seconds : -1;
		} catch (ArithmeticException e) {
			return -1;
		}
	}

