			case '-':
				if (i + 4 < rev.length && rev[i + 1] == 'g'
						&& isHex(rev[i + 2]) && isHex(rev[i + 3])) {
					int cnt = 2;
					while (i + 2 + cnt < rev.length && isHex(rev[i + 2 + cnt]))
						cnt++;
					String s = new String(rev, i + 2, cnt);
					if (AbbreviatedObjectId.isId(s)) {
						ObjectId id = resolveAbbreviation(s);
						if (id != null) {
							ref = rw.parseAny(id);
							i += 1 + s.length();
						}
					}
				}
				break;
