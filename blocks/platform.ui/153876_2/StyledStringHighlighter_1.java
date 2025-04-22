				if (!quoting) {
					sb.append(QUOTE_START);
					quoting = true;
				}
				if (prevChar && Character.isUpperCase(c)) {
					sb.append(QUOTE_END);
					sb.append(DOT_STAR_LAZY);
					sb.append(QUOTE_START);
				}
				if (c != TERMINATOR) {
					sb.append(c);
				}
				if (i == len - 1) {
					sb.append(QUOTE_END);
					quoting = false;
				}
