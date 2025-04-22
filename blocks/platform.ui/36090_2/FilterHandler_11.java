
	private boolean containsWildcards(String pattern) {
		for (int i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (c != '.' && !Character.isJavaIdentifierPart(c)) {
				return true;
			}
		}
		return false;
	}

	private static Pattern createPattern(String pattern) throws PatternSyntaxException {
		int len = pattern.length();
		StringBuilder buf = new StringBuilder(len * 2);
		boolean isEscaped = false;
		for (int i = 0; i < len; i++) {
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
		}
		if (isEscaped) {
		    buf.append(DOUBLE_BACKSLASH);
		    isEscaped= false;
		}
		return Pattern.compile(buf.toString());
	}
