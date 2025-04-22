				if (escapeFlag) {
					addCharacterHead(heads
					escapeFlag = false;
				} else {
					final AbstractHead head = createWildCardHead(
							invalidWildgetCharacter
					heads.add(head);
				}
				break;
			}
			case '\\': {
				if (escapeFlag
						|| (invalidWildgetCharacter != null && invalidWildgetCharacter == '\\')) {
					addCharacterHead(heads
					escapeFlag = false;
				} else {
					escapeFlag = true;
				}
