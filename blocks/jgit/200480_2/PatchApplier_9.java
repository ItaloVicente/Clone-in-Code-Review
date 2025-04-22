				case ' ':
				case '-':
					if (pos >= limit
							|| !newLines.get(pos).equals(slice(hunkLine
						return false;
					}
					pos++;
					break;
				default:
					break;
