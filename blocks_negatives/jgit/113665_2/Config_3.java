			case ' ':
				if (!inquote && (r.length() == 0 || r.charAt(r.length() - 1) == ' ')) {
					r.insert(lineStart, '"');
					inquote = true;
				}
				r.append(' ');
				break;

