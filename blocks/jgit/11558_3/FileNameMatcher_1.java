				escaped = false;
			} else {
				switch (c) {
				case '*': {
					final AbstractHead head = createWildCardHead(
							invalidWildgetCharacter
					heads.add(head);
					break;
				}
				case '?': {
					final AbstractHead head = createWildCardHead(
							invalidWildgetCharacter
					heads.add(head);
					break;
				}
				case '\\':
					escaped = true;
					break;
				default:
					final CharacterHead head = new CharacterHead(c);
					heads.add(head);
				}
