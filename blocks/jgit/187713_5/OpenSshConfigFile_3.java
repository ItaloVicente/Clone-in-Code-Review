				escaped = false;
				break;
			case '\\':
				if (escaped) {
					b.append(ch);
				}
				escaped = !escaped;
				break;
			case ' ':
				if (quote == 0) {
					if (escaped) {
						b.append(ch);
						escaped = false;
					} else {
						break SCAN;
					}
				} else {
					if (escaped) {
						b.append('\\');
					}
					b.append(ch);
					escaped = false;
				}
				break;
			default:
				if (escaped) {
					b.append('\\');
				}
				if (quote == 0 && Character.isWhitespace(ch)) {
					break SCAN;
