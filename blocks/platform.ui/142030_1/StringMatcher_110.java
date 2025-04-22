					fBound += buf.length();
					buf.setLength(0);
				}
				break;
			case '?':
				buf.append(fSingleWildCard);
				break;
			default:
				buf.append(c);
			}
		}

		if (buf.length() > 0) {
