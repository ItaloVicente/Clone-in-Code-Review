			case '^':
				if (seenEscape || in_brackets > 0)
					sb.append(c);
				else
					sb.append('\\').append(c);
				break;

