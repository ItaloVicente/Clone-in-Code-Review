	public static String normalizeBranchName(String name) {
		if (name == null || name.isEmpty()) {
			return name;
		}
		String result = name.trim();
		String fullName = result.startsWith(Constants.R_HEADS) ? result
				: Constants.R_HEADS + result;
		if (isValidRefName(fullName)) {
			return result;
		}

		result = result.replaceAll("(?:\\h|\\v)+"
		StringBuilder b = new StringBuilder();
		char p = '/';
		for (int i = 0
			char c = result.charAt(i);
			if (c < ' ' || c == 127) {
				continue;
			}
			switch (c) {
			case '\\':
			case '^':
			case '~':
			case ':':
			case '?':
			case '*':
			case '[':
			case '@':
			case '<':
			case '>':
			case '|':
			case '"':
				c = '-';
				break;
			default:
				break;
			}
			switch (c) {
			case '/':
				if (p == '/') {
					continue;
				}
				p = '/';
				break;
			case '.':
			case '_':
			case '-':
				if (p == '/' || p == '-') {
					continue;
				}
				p = '-';
				break;
			default:
				p = c;
				break;
			}
			b.append(c);
		}
		result = b.toString().replaceFirst("[/_.-]+$"
				.replaceAll("\\.lock($|/)"
		return FORBIDDEN_BRANCH_NAME_COMPONENTS.matcher(result)
	}

