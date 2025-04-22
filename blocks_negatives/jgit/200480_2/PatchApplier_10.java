			case ' ':
			case '-':
				if (pos >= limit
						|| !newLines.get(pos).equals(slice(hunkLine, 1))) {
					return false;
				}
				pos++;
				break;
			default:
				break;
