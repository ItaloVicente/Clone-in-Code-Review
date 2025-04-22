			char c = pattern.charAt(i);
			switch (c) {
			case '\\':
				if (!isEscaped) {
					isEscaped = true;
				} else {
					buf.append(DOUBLE_BACKSLASH);
					isEscaped = false;
				}
				break;
			case '(':
			case ')':
			case '{':
			case '}':
			case '.':
			case '[':
			case ']':
			case '$':
			case '^':
			case '+':
			case '|':
				if (isEscaped) {
					buf.append(DOUBLE_BACKSLASH);
					isEscaped = false;
				}
				buf.append('\\');
				buf.append(c);
				break;
			case '?':
				if (!isEscaped) {
					buf.append('.');
				} else {
					buf.append('\\');
					buf.append(c);
					isEscaped = false;
				}
				break;
			case '*':
				if (!isEscaped) {
					buf.append(".*"); //$NON-NLS-1$
				} else {
					buf.append('\\');
					buf.append(c);
					isEscaped = false;
				}
				break;
			default:
				if (isEscaped) {
					buf.append(DOUBLE_BACKSLASH);
					isEscaped = false;
				}
				buf.append(c);
				break;
			}
